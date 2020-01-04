package ru.sokomishalov.memeory.api.scheduler

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
import reactor.core.publisher.Mono
import ru.sokomishalov.commons.core.common.unit
import ru.sokomishalov.commons.core.log.Loggable
import ru.sokomishalov.commons.core.reactor.aMono
import ru.sokomishalov.commons.core.serialization.YAML_OBJECT_MAPPER
import ru.sokomishalov.commons.distributed.locks.DistributedLockProvider
import ru.sokomishalov.commons.distributed.locks.withDistributedLock
import ru.sokomishalov.memeory.api.autoconfigure.MemeoryProperties
import ru.sokomishalov.memeory.core.dto.ChannelDTO
import ru.sokomishalov.memeory.core.dto.MemeDTO
import ru.sokomishalov.memeory.core.dto.TopicDTO
import ru.sokomishalov.memeory.db.ChannelService
import ru.sokomishalov.memeory.db.MemeService
import ru.sokomishalov.memeory.db.TopicService
import ru.sokomishalov.memeory.providers.ProviderFactory
import ru.sokomishalov.memeory.telegram.bot.MemeoryBot
import java.util.*
import kotlin.concurrent.schedule

/**
 * @author sokomishalov
 */
@Service
class MemesFetchingScheduler(
        private val props: MemeoryProperties,
        private val channelService: ChannelService,
        private val memeService: MemeService,
        private val topicService: TopicService,
        private val providerFactory: ProviderFactory,
        private val bot: MemeoryBot,
        private val lockProvider: DistributedLockProvider,
        @Value("classpath:defaults/channels.yml")
        private val defaultChannels: Resource,
        @Value("classpath:defaults/topics.yml")
        private val defaultTopics: Resource
) {

    companion object : Loggable

    @EventListener(ApplicationReadyEvent::class)
    fun onApplicationStartUp(): Mono<Unit> = aMono {
        storeDefaults()
        Timer(true).schedule(delay = 0, period = props.fetchInterval.toMillis()) {
            GlobalScope.launch {
                loadMemes()
            }
        }.unit()
    }

    private suspend fun storeDefaults() {
        withContext(IO) {
            val channels = YAML_OBJECT_MAPPER.readValue<Array<ChannelDTO>>(defaultChannels.inputStream)
            channelService.save(*channels)

            val topics = YAML_OBJECT_MAPPER.readValue<Array<TopicDTO>>(defaultTopics.inputStream)
            topicService.save(*topics)
        }
    }

    suspend fun loadMemes() {
        log("About to fetch some new memes")

        val fetchedMemes = channelService
                .findAll()
                .map { fetchMemesClusterable(it) }
                .flatten()

        val savedMemes = memeService.save(fetchedMemes, props.memeLifeTime)
        logInfo("Finished fetching memes. Total fetched: ${fetchedMemes.size}. Total saved: ${savedMemes.size}.")

        bot.broadcastMemes(savedMemes)
        logInfo("Finished broadcasting memes by bot")
    }

    private suspend fun fetchMemesClusterable(channel: ChannelDTO, orElse: List<MemeDTO> = emptyList()): List<MemeDTO> {
        return when {
            props.useClusterLocks -> {
                lockProvider.withDistributedLock(
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
        val providerService = providerFactory[channel.provider]
        return when {
            providerService != null -> runCatching {
                providerService
                        .fetchMemes(channel, props.fetchLimit)
                        .map { it.apply { it.channelId = channel.id } }
            }.getOrElse {
                logWarn(it)
                orElse
            }
            else -> orElse
        }
    }
}
