package ru.sokomishalov.memeory.service.mongo

import org.springframework.stereotype.Service
import reactor.bool.not
import reactor.core.publisher.Flux
import reactor.core.publisher.Flux.fromArray
import reactor.core.publisher.Mono
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.entity.Channel
import ru.sokomishalov.memeory.repository.ChannelRepository
import ru.sokomishalov.memeory.service.ChannelService
import ru.sokomishalov.memeory.mapper.ChannelMapper.Companion.INSTANCE as channelMapper

/**
 * @author sokomishalov
 */
@Service
class MongoChannelService(private val repository: ChannelRepository) : ChannelService {
    override fun saveOne(channel: ChannelDTO): Mono<ChannelDTO> {
        return Mono
                .just(channel)
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

    override fun findAll(): Flux<ChannelDTO> {
        return repository
                .findAll()
                .map { channelMapper.toDto(it) }
    }
}
