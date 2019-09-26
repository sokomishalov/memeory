package ru.sokomishalov.memeory.service.scheduler

import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import ru.sokomishalov.commons.core.collections.aFilter
import ru.sokomishalov.commons.core.collections.aForEach
import ru.sokomishalov.commons.core.collections.aMap
import ru.sokomishalov.commons.core.consts.DEFAULT_DATE_FORMATTER
import ru.sokomishalov.commons.core.images.checkImageUrl
import ru.sokomishalov.commons.core.log.Loggable
import ru.sokomishalov.commons.core.serialization.YAML_OBJECT_MAPPER
import ru.sokomishalov.memeory.autoconfigure.MemeoryProperties
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.service.db.ChannelService
import ru.sokomishalov.memeory.service.db.MemeService
import ru.sokomishalov.memeory.service.provider.ProviderService
import java.time.LocalDateTime.now
import java.util.*
import javax.annotation.PostConstruct
import kotlin.concurrent.schedule


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
            val channelDTOs = withContext(IO) {
                YAML_OBJECT_MAPPER.readValue<Array<ChannelDTO>>(resource.inputStream)
            }
            channelService.saveIfNotExist(*channelDTOs)
        }
    }

    // FIXME make clusterable
    @EventListener(ApplicationReadyEvent::class)
    fun startUpCoroutine() {
        Timer(true).schedule(delay = 0, period = props.fetchIntervalMs) {
            log("About to fetch some new memes at ${now().format(DEFAULT_DATE_FORMATTER)}")

            GlobalScope.launch {
                val channels = channelService.findAllEnabled()

                channels.aForEach { channel ->
                    val providerService = providerServices.find { it.sourceType() == channel.sourceType }

                    val fetchedMemes = runCatching {
                        providerService?.fetchMemesFromChannel(channel) ?: emptyList()
                    }.getOrElse { emptyList() }

                    val memesToSave = fetchedMemes
                            .aFilter {
                                it.attachments.all { att -> checkImageUrl(att.url) }
                            }.aMap {
                                it.apply {
                                    it.channelId = channel.id
                                    it.channelName = channel.name
                                }
                            }

                    memeService.saveMemesIfNotExist(memesToSave)
                }
            }
        }
    }
}
