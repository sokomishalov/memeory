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
import ru.sokomishalov.commons.core.collections.aMap
import ru.sokomishalov.commons.core.log.Loggable
import ru.sokomishalov.commons.core.serialization.YAML_OBJECT_MAPPER
import ru.sokomishalov.commons.spring.locks.cluster.LockProvider
import ru.sokomishalov.commons.spring.locks.cluster.withClusterLock
import ru.sokomishalov.memeory.autoconfigure.MemeoryProperties
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.service.db.ChannelService
import ru.sokomishalov.memeory.service.db.MemeService
import ru.sokomishalov.memeory.service.provider.ProviderService
import java.util.*
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
        private val lockProvider: LockProvider,
        @Value("classpath:channels.yml")
        private val resource: Resource
) : Loggable {

    @EventListener(ApplicationReadyEvent::class)
    fun fetchMemes() {
        storeDefaultChannels()

        Timer(true).schedule(delay = 0, period = props.fetchInterval.toMillis()) {
            log("About to fetch some new memes")

            GlobalScope.launch {
                val fetchedMemes = channelService
                        .findAllEnabled()
                        .aMap { fetchMemesByChannel(it) }
                        .flatten()

                val savedMemes = memeService.saveMemesIfNotExist(fetchedMemes)
                log("Finished fetching memes. Total fetched: ${fetchedMemes.size}. Total saved: ${savedMemes.size}.")
            }
        }
    }

    private fun storeDefaultChannels() {
        GlobalScope.launch {
            val channelDTOs = withContext(IO) {
                YAML_OBJECT_MAPPER.readValue<Array<ChannelDTO>>(resource.inputStream)
            }
            channelService.saveIfNotExist(*channelDTOs)
        }
    }

    private suspend fun fetchMemesByChannel(channel: ChannelDTO, orElse: List<MemeDTO> = emptyList()): List<MemeDTO> {
        return lockProvider.withClusterLock(
                lockName = channel.id,
                lockAtLeastFor = props.fetchInterval.minusMinutes(1)
        ) {
            val providerService = providerServices.find { it.sourceType() == channel.sourceType }
            when {
                providerService != null -> runCatching {
                    providerService
                            .fetchMemesFromChannel(channel)
                            .map {
                                it.apply {
                                    it.channelId = channel.id
                                    it.channelName = channel.name
                                }
                            }
                }.getOrElse {
                    logWarn(it)
                    orElse
                }
                else -> orElse
            }
        } ?: orElse
    }
}
