package ru.sokomishalov.memeory.service.scheduler

import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import ru.sokomishalov.memeory.config.MemeoryProperties
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.service.db.ChannelService
import ru.sokomishalov.memeory.service.db.MemeService
import ru.sokomishalov.memeory.service.provider.ProviderService
import ru.sokomishalov.memeory.util.DATE_FORMATTER
import ru.sokomishalov.memeory.util.extensions.await
import ru.sokomishalov.memeory.util.extensions.pFilter
import ru.sokomishalov.memeory.util.extensions.pForEach
import ru.sokomishalov.memeory.util.extensions.pMap
import ru.sokomishalov.memeory.util.io.checkAttachmentAvailabilityAsync
import ru.sokomishalov.memeory.util.log.Loggable
import ru.sokomishalov.memeory.util.log.measureTime
import ru.sokomishalov.memeory.util.serialization.YAML_OBJECT_MAPPER
import java.time.LocalDateTime.now
import java.util.*
import javax.annotation.PostConstruct
import kotlin.concurrent.schedule
import reactor.core.publisher.Flux.fromIterable as fluxFromIterable


/**
 * @author sokomishalov
 */
@Service
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
        GlobalScope.launch {
            val channelDTOs = YAML_OBJECT_MAPPER.readValue<Array<ChannelDTO>>(resource.inputStream)
            channelService.saveIfNotExist(*channelDTOs).await()
        }
    }

    // FIXME make clusterable
    @EventListener(ApplicationReadyEvent::class)
    fun startUpCoroutine() {
        Timer().schedule(delay = 0, period = props.fetchIntervalMs) {
            log("About to fetch some new memes at ${now().format(DATE_FORMATTER)}")

            GlobalScope.launch {
                measureTime("Finished downloading new memes after:") {
                    val channels = channelService.findAllEnabled().await()

                    channels.pForEach { channel ->
                        val providerService = providerServices.find { it.sourceType() == channel.sourceType }

                        val fetchedMemes = providerService
                                ?.fetchMemesFromChannel(channel)
                                ?.limitRequest(props.fetchCount.toLong())
                                .await()

                        val memesToSave = fetchedMemes
                                .pFilter {
                                    it?.attachments?.all { att -> checkAttachmentAvailabilityAsync(att.url) } ?: false
                                }
                                .pMap {
                                    it.apply {
                                        it.channelId = channel.id
                                        it.channelName = channel.name
                                    }
                                }

                        memeService.saveMemesIfNotExist(fluxFromIterable(memesToSave)).then().await()
                    }
                }
            }
        }
    }
}
