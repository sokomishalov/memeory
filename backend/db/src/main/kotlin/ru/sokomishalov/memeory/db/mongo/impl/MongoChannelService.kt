package ru.sokomishalov.memeory.db.mongo.impl

import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import ru.sokomishalov.commons.core.reactor.await
import ru.sokomishalov.memeory.core.dto.ChannelDTO
import ru.sokomishalov.memeory.core.enums.Provider
import ru.sokomishalov.memeory.db.ChannelService
import ru.sokomishalov.memeory.db.mongo.mapper.ChannelMapper
import ru.sokomishalov.memeory.db.mongo.repository.ChannelRepository

/**
 * @author sokomishalov
 */
@Service
@Primary
class MongoChannelService(
        private val repository: ChannelRepository,
        private val channelMapper: ChannelMapper
) : ChannelService {

    override suspend fun findAll(): List<ChannelDTO> {
        return repository
                .findAll()
                .await()
                .let { channelMapper.toDtoList(it) }
    }

    override suspend fun findById(id: String): ChannelDTO? {
        return repository
                .findById(id)
                .await()
                ?.let { channelMapper.toDto(it) }
    }

    override suspend fun findByTopics(vararg topics: String): List<ChannelDTO> {
        return repository
                .findAllByTopicsIn(topics.toList())
                .await()
                .let { channelMapper.toDtoList(it) }
    }

    override suspend fun findByProvider(providerId: Provider): List<ChannelDTO> {
        return repository
                .findAllByProvider(providerId)
                .await()
                .let { channelMapper.toDtoList(it) }
    }

    override suspend fun save(vararg batch: ChannelDTO): List<ChannelDTO> {
        val channelsToSave = batch.toList().map { channelMapper.toEntity(it) }
        val savedChannels = repository.saveAll(channelsToSave).await()
        return savedChannels.map { channelMapper.toDto(it) }
    }
}
