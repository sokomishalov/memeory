package ru.sokomishalov.memeory.api.scheduler

import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.sokomishalov.commons.core.log.Loggable
import ru.sokomishalov.commons.core.reactor.aMono
import ru.sokomishalov.commons.core.scheduled.runScheduled
import ru.sokomishalov.commons.core.serialization.YAML_OBJECT_MAPPER
import ru.sokomishalov.memeory.api.autoconfigure.MemeoryProperties
import ru.sokomishalov.memeory.core.dto.ChannelDTO
import ru.sokomishalov.memeory.core.dto.MemeDTO
import ru.sokomishalov.memeory.core.dto.TopicDTO
import ru.sokomishalov.memeory.db.ChannelService
import ru.sokomishalov.memeory.db.MemeService
import ru.sokomishalov.memeory.db.TopicService
import ru.sokomishalov.memeory.providers.ProviderFactory
import ru.sokomishalov.memeory.providers.fetchMemes
import ru.sokomishalov.memeory.telegram.bot.MemeoryBot
import java.time.Duration

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
        @Value("classpath:defaults/channels.yml")
        private val defaultChannels: Resource,
        @Value("classpath:defaults/topics.yml")
        private val defaultTopics: Resource
) {

    companion object : Loggable

    @EventListener(ApplicationReadyEvent::class)
    fun onApplicationStartUp(): Mono<Any> = aMono {
        storeDefaults()
        runScheduled(delay = Duration.ZERO, interval = props.fetchInterval) {
            loadMemes()
        }
    }

    private suspend fun storeDefaults() {
        val channels = YAML_OBJECT_MAPPER.readValue<Array<ChannelDTO>>(defaultChannels.inputStream)
        val topics = YAML_OBJECT_MAPPER.readValue<Array<TopicDTO>>(defaultTopics.inputStream)

        channelService.save(*channels)
        topicService.save(*topics)
    }

    suspend fun loadMemes() {
        logInfo { "About to fetch some new memes" }

        val channels = channelService.findAll()
        val fetchedMemes = channels.flatMap { fetchMemes(channel = it) }

        val savedMemes = memeService.save(batch = fetchedMemes, ttl = props.memeLifeTime)
        logInfo { "Finished fetching memes. Total fetched: ${fetchedMemes.size}. Total saved: ${savedMemes.size}." }

        bot.broadcastMemes(savedMemes)
        logInfo { "Finished broadcasting memes by bot" }
    }

    private suspend fun fetchMemes(channel: ChannelDTO): List<MemeDTO> {
        return runCatching {
            providerFactory[channel.provider]
                    .fetchMemes(channel = channel, limit = props.fetchLimit)
                    .map { it.apply { it.channelId = channel.id } }
        }.onFailure {
            logWarn(it)
        }.getOrElse {
            emptyList()
        }
    }
}
