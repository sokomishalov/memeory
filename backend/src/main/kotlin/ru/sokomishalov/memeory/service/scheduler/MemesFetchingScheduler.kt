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
import ru.sokomishalov.commons.core.log.Loggable
import ru.sokomishalov.commons.core.serialization.YAML_OBJECT_MAPPER
import ru.sokomishalov.commons.spring.locks.cluster.LockProvider
import ru.sokomishalov.commons.spring.locks.cluster.withClusterLock
import ru.sokomishalov.memeory.autoconfigure.MemeoryProperties
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.service.db.ChannelService
import ru.sokomishalov.memeory.service.db.MemeService
import ru.sokomishalov.memeory.service.provider.ProviderFactory
import java.util.*
import kotlin.concurrent.schedule

/**
 * @author sokomishalov
 */
@Service
class MemesFetchingScheduler(
        private val channelService: ChannelService,
        private val props: MemeoryProperties,
        private val providerFactory: ProviderFactory,
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
                        .map { fetchMemesClusterable(it) }
                        .flatten()

                val savedMemes = memeService.saveBatch(fetchedMemes)
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

    private suspend fun fetchMemesClusterable(channel: ChannelDTO, orElse: List<MemeDTO> = emptyList()): List<MemeDTO> {
        return when {
            props.useClusterLocks -> {
                lockProvider.withClusterLock(
                        lockName = channel.id,
                        lockAtLeastFor = props.fetchInterval.minusMinutes(1)
                ) {
                    fetchMemes(channel, orElse)
                } ?: orElse
            }
            else -> fetchMemes(channel, orElse)
        }

    }

    private suspend fun fetchMemes(channel: ChannelDTO, orElse: List<MemeDTO>): List<MemeDTO> {
        val providerService = providerFactory.getService(channel.provider)
        return when {
            providerService != null -> runCatching {
                providerService
                        .fetchMemes(channel)
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
    }
}
