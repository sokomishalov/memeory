package ru.sokomishalov.memeory.service

import com.fasterxml.jackson.core.type.TypeReference
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux.empty
import reactor.core.publisher.Flux.fromIterable
import reactor.core.publisher.Mono.just
import ru.sokomishalov.memeory.config.props.MemeoryProperties
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.service.api.ApiService
import ru.sokomishalov.memeory.util.DATE_FORMATTER
import ru.sokomishalov.memeory.util.ObjectMapperHelper
import ru.sokomishalov.memeory.util.loggerFor
import java.time.Duration.ZERO
import java.time.Duration.ofMillis
import java.time.LocalDateTime.now
import javax.annotation.PostConstruct
import reactor.core.publisher.Flux.interval as scheduled


/**
 * @author sokomishalov
 */
@Service
class MemesFetchingService(
        private val channelService: ChannelService,
        private val props: MemeoryProperties,
        private val apiServices: List<ApiService>,
        private val memeService: MemeService,
        @Value("classpath:channels.yml")
        private val resource: Resource
) {
    companion object {
        private val log: Logger = loggerFor(MemesFetchingService::class.java)
    }

    @PostConstruct
    fun init() {
        just(object : TypeReference<Array<ChannelDTO>>() {})
                .map { ObjectMapperHelper.yamlObjectMapper.readValue(resource.inputStream, it) as Array<ChannelDTO> }
                .flatMapMany { channelService.saveChannelsIfNotExist(*it) }
                .then()
                .subscribe()
    }

    // FIXME make clusterable
    @EventListener(ApplicationReadyEvent::class)
    fun startUp() {
        scheduled(ZERO, ofMillis(props.fetchIntervalMs))
                .doOnNext { log.info("About to fetch some new memes at ${now().format(DATE_FORMATTER)}") }
                .flatMap { channelService.findAllEnabled() }
                .flatMap { channel ->
                    fromIterable(apiServices)
                            .filter { it.sourceType() == channel.sourceType }
                            .flatMap { it.fetchMemesFromChannel(channel) }
                            .doOnNext {
                                it.channelId = channel.id
                                it.channelName = channel.name
                            }
                            .onErrorResume { empty() }
                            .let { memeService.saveMemesIfNotExist(it) }
                }
                .then()
                .subscribe()
    }
}
