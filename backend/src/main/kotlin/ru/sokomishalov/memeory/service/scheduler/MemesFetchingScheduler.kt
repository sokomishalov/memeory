package ru.sokomishalov.memeory.service.scheduler

import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux.empty
import reactor.core.publisher.Flux.fromIterable
import reactor.core.publisher.Mono.just
import ru.sokomishalov.memeory.condition.ConditionalOnNotUsingCoroutines
import ru.sokomishalov.memeory.config.MemeoryProperties
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.service.db.ChannelService
import ru.sokomishalov.memeory.service.db.MemeService
import ru.sokomishalov.memeory.service.provider.ProviderService
import ru.sokomishalov.memeory.util.DATE_FORMATTER
import ru.sokomishalov.memeory.util.EMPTY
import ru.sokomishalov.memeory.util.io.checkAttachmentAvailability
import ru.sokomishalov.memeory.util.log.Loggable
import ru.sokomishalov.memeory.util.serialization.YAML_OBJECT_MAPPER
import java.time.Duration.ZERO
import java.time.Duration.ofMillis
import java.time.LocalDateTime.now
import javax.annotation.PostConstruct
import reactor.core.publisher.Flux.interval as scheduled


/**
 * @author sokomishalov
 */
@Service
@ConditionalOnNotUsingCoroutines
class MemesFetchingScheduler(
        private val channelService: ChannelService,
        private val props: MemeoryProperties,
        private val providerServices: List<ProviderService>,
        private val memeService: MemeService,
        @Value("classpath:channels.yml")
        private val resource: Resource
) : Loggable {

    @PostConstruct
    fun init() {
        just(EMPTY)
                .map { YAML_OBJECT_MAPPER.readValue<Array<ChannelDTO>>(resource.inputStream) }
                .flatMapMany { channelService.saveIfNotExist(*it) }
                .then()
                .subscribe()
    }

    // FIXME make clusterable
    @EventListener(ApplicationReadyEvent::class)
    fun startUp() {
        scheduled(ZERO, ofMillis(props.fetchIntervalMs))
                .doOnNext { log("About to fetch some new memes at ${now().format(DATE_FORMATTER)}") }
                .flatMap { channelService.findAllEnabled() }
                .flatMap { channel ->
                    fromIterable(providerServices)
                            .filter { it.sourceType() == channel.sourceType }
                            .flatMap {
                                it.fetchMemesFromChannel(channel).limitRequest(props.fetchCount.toLong())
                            }
                            .doOnNext {
                                it.channelId = channel.id
                                it.channelName = channel.name
                            }
                            .filter {
                                it?.attachments?.all { att ->
                                    checkAttachmentAvailability(att.url ?: EMPTY)
                                } ?: false
                            }
                            .onErrorResume { empty() }
                            .let { memeService.saveMemesIfNotExist(it) }
                }
                .then()
                .subscribe()
    }
}
