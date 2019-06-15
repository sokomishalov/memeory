package ru.sokomishalov.memeory.service.mongo

import org.springframework.stereotype.Service
import reactor.bool.not
import reactor.core.publisher.Flux
import reactor.core.publisher.Flux.fromArray
import reactor.core.publisher.Mono
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.entity.Channel
import ru.sokomishalov.memeory.mapper.ChannelMapper
import ru.sokomishalov.memeory.repository.SourceRepository
import ru.sokomishalov.memeory.service.ChannelService

/**
 * @author sokomishalov
 */
@Service
class MongoChannelService(private val repository: SourceRepository) : ChannelService {
    override fun saveOne(channel: ChannelDTO): Mono<ChannelDTO> {
        return Mono
                .just(channel)
                .map { ChannelMapper.INSTANCE.toEntity(it) }
                .flatMap { repository.save(it) }
                .map { ChannelMapper.INSTANCE.toDto(it) }

    }

    override fun saveChannelsIfNotExist(vararg channels: ChannelDTO): Flux<ChannelDTO> {
        val channelsToSaveFlux: Flux<Channel> = fromArray(channels)
                .filterWhen { !repository.existsById(it.id) }
                .map { ChannelMapper.INSTANCE.toEntity(it) }

        return repository
                .saveAll(channelsToSaveFlux)
                .map { ChannelMapper.INSTANCE.toDto(it) }
    }

    override fun findAll(): Flux<ChannelDTO> {
        return repository
                .findAll()
                .map { ChannelMapper.INSTANCE.toDto(it) }
    }
}
