package ru.sokomishalov.memeory.service.scheduler.coroutine

import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import ru.sokomishalov.memeory.condition.ConditionalOnUsingCoroutines
import ru.sokomishalov.memeory.config.MemeoryProperties
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.service.db.ChannelService
import ru.sokomishalov.memeory.service.db.MemeService
import ru.sokomishalov.memeory.service.provider.ProviderService
import ru.sokomishalov.memeory.util.DATE_FORMATTER
import ru.sokomishalov.memeory.util.extensions.await
import ru.sokomishalov.memeory.util.extensions.coFilter
import ru.sokomishalov.memeory.util.extensions.coForEach
import ru.sokomishalov.memeory.util.extensions.coMap
import ru.sokomishalov.memeory.util.io.coCheckAttachmentAvailability
import ru.sokomishalov.memeory.util.log.Loggable
import ru.sokomishalov.memeory.util.log.measureTimeAndReturn
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
@ConditionalOnUsingCoroutines
class CoroutineMemesFetchingScheduler(
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
        Timer(true).schedule(delay = 0, period = props.fetchIntervalMs) {
            log("About to fetch some new memes at ${now().format(DATE_FORMATTER)}")

            GlobalScope.launch {
                measureTimeAndReturn("Finished to download new memes after:") {
                    val channels = channelService.findAllEnabled().await()

                    channels.coForEach { channel ->
                        val providerService = providerServices.find { it.sourceType() == channel.sourceType }

                        val fetchedMemes = try {
                            providerService
                                    ?.fetchMemesFromChannel(channel)
                                    ?.limitRequest(props.fetchCount.toLong())
                                    .await()
                        } catch (e: Exception) {
                            emptyList()
                        }

                        val memesToSave = fetchedMemes
                                .coFilter {
                                    it?.attachments?.all { att -> coCheckAttachmentAvailability(att.url) } ?: false
                                }
                                .coMap {
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
