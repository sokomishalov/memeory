package ru.sokomishalov.memeory.service

import com.fasterxml.jackson.core.type.TypeReference
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux.*
import reactor.core.scheduler.Schedulers.newSingle
import ru.sokomishalov.memeory.config.props.MemeoryProperties
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.service.api.ApiService
import ru.sokomishalov.memeory.util.ObjectMapperHelper
import ru.sokomishalov.memeory.util.loggerFor
import java.lang.Thread.sleep
import java.time.Duration.ofMillis
import javax.annotation.PostConstruct
import reactor.core.publisher.Flux.interval as scheduled


/**
 * @author sokomishalov
 */
@Service
class MemesFetchingService(
        private val channelService: ChannelService,
        private val memeoryProperties: MemeoryProperties,
        private val apiServices: List<ApiService>,
        private val memeService: MemeService,
        @Value("classpath:channels.yml")
        private val resource: Resource
) {

    companion object {
        private val log: Logger = loggerFor(this::class.java)
    }

    // FIXME make non blocking
    // FIXME make clusterable
    @PostConstruct
    fun init() {
        val sourcesTypeRef: TypeReference<Array<ChannelDTO>> = object : TypeReference<Array<ChannelDTO>>() {}
        val sourcesFromYaml: Array<ChannelDTO> = ObjectMapperHelper.yamlObjectMapper.readValue(resource.inputStream, sourcesTypeRef)

        channelService
                .saveChannelsIfNotExist(*sourcesFromYaml)
                .then()
                .subscribe()

        sleep(100)

        scheduled(ofMillis(memeoryProperties.fetchIntervalMs))
                .flatMap { channelService.findAll() }
                .flatMap { channel ->
                    val fetchedMemesFlux = fromIterable(apiServices)
                            .filter { it.sourceType() == channel.sourceType }
                            .flatMap { it.fetchMemesFromChannels(channel) }
                            .doOnNext { it.channel = channel.name }

                    memeService
                            .saveMemesIfNotExist(fetchedMemesFlux)
                            .onErrorResume { t ->
                                log.debug(t.message)
                                empty()
                            }
                }
                .then()
                .subscribeOn(newSingle(javaClass.simpleName))
                .subscribe()

        sleep(100)
    }
}
