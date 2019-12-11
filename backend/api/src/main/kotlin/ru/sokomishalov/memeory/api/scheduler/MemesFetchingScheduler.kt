package ru.sokomishalov.memeory.api.scheduler

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
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
import ru.sokomishalov.commons.core.serialization.buildComplexObjectMapper
import ru.sokomishalov.commons.spring.locks.cluster.LockProvider
import ru.sokomishalov.commons.spring.locks.cluster.withClusterLock
import ru.sokomishalov.memeory.api.autoconfigure.MemeoryProperties
import ru.sokomishalov.memeory.core.dto.ChannelDTO
import ru.sokomishalov.memeory.core.dto.MemeDTO
import ru.sokomishalov.memeory.db.ChannelService
import ru.sokomishalov.memeory.db.MemeService
import ru.sokomishalov.memeory.providers.ProviderFactory
import ru.sokomishalov.memeory.telegram.bot.MemeoryBot
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
        private val defaultChannels: Resource,
        private val bot: MemeoryBot
) {

    companion object : Loggable {
        val YAML_OBJECT_MAPPER: ObjectMapper = buildComplexObjectMapper(YAMLFactory())
    }

    @EventListener(ApplicationReadyEvent::class)
    fun onApplicationStartUp(): Mono<Unit> = aMono {
        storeDefaultChannels()
        Timer(true).schedule(delay = props.fetchInterval.toMillis(), period = props.fetchInterval.toMillis()) {
            GlobalScope.launch {
                loadMemes()
            }
        }.unit()
    }

    suspend fun loadMemes() {
        log("About to fetch some new memes")

        val fetchedMemes = channelService
                .findAllEnabled()
                .map { fetchMemesClusterable(it) }
                .flatten()

        val savedMemes = memeService.saveBatch(fetchedMemes, props.memeLifeTime)
        log("Finished fetching memes. Total fetched: ${fetchedMemes.size}. Total saved: ${savedMemes.size}.")

        bot.broadcastMemes(savedMemes)
        log("Finished broadcasting memes by bot")
    }

    private suspend fun storeDefaultChannels() {
        val channels = withContext(IO) { YAML_OBJECT_MAPPER.readValue<Array<ChannelDTO>>(defaultChannels.inputStream) }
        channelService.saveBatch(*channels)
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
                        .fetchMemes(channel, props.fetchLimit)
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
