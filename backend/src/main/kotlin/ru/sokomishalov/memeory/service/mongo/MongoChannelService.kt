package ru.sokomishalov.memeory.service.mongo

import org.springframework.stereotype.Service
import reactor.bool.not
import reactor.core.publisher.Flux
import reactor.core.publisher.Flux.fromArray
import reactor.core.publisher.Flux.fromIterable
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.entity.Channel
import ru.sokomishalov.memeory.repository.ChannelRepository
import ru.sokomishalov.memeory.service.ChannelService
import ru.sokomishalov.memeory.service.api.ApiService
import ru.sokomishalov.memeory.util.CHANNEL_LOGO_CACHE_KEY
import springfox.documentation.annotations.Cacheable
import ru.sokomishalov.memeory.mapper.ChannelMapper.Companion.INSTANCE as channelMapper


/**
 * @author sokomishalov
 */
@Service
class MongoChannelService(private val repository: ChannelRepository,
                          private val apiServices: List<ApiService>) : ChannelService {

    override fun saveOne(channel: ChannelDTO): Mono<ChannelDTO> {
        return just(channel)
                .map { channelMapper.toEntity(it) }
                .flatMap { repository.save(it) }
                .map { channelMapper.toDto(it) }

    }

    override fun saveChannelsIfNotExist(vararg channels: ChannelDTO): Flux<ChannelDTO> {
        val channelsToSaveFlux: Flux<Channel> = fromArray(channels)
                .filterWhen { !repository.existsById(it.id) }
                .map { channelMapper.toEntity(it) }

        return repository
                .saveAll(channelsToSaveFlux)
                .map { channelMapper.toDto(it) }
    }

    override fun findAllEnabled(): Flux<ChannelDTO> {
        return repository
                .findAll()
                .filter { it.enabled }
                .map { channelMapper.toDto(it) }
    }

    /*
     * TODO rewrite cache to reactor addons
     */
    @Cacheable(CHANNEL_LOGO_CACHE_KEY)
    override fun getLogoByChannelId(channelId: String): Mono<ByteArray> {
        return repository
                .findById(channelId)
                .map { channelMapper.toDto(it) }
                .flatMap { c ->
                    fromIterable(apiServices)
                            .filter { it.sourceType() == c.sourceType }
                            .next()
                            .flatMap { it.getLogoByChannel(c) }
                }
                .cache()
    }
}
