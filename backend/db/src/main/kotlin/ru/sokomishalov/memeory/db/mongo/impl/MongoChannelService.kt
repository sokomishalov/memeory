package ru.sokomishalov.memeory.db.mongo.impl

import org.springframework.context.annotation.Primary
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.Update.update
import org.springframework.stereotype.Service
import ru.sokomishalov.commons.core.reactor.await
import ru.sokomishalov.commons.core.reactor.awaitStrict
import ru.sokomishalov.memeory.core.dto.ChannelDTO
import ru.sokomishalov.memeory.core.util.consts.MONGO_ID_FIELD
import ru.sokomishalov.memeory.db.ChannelService
import ru.sokomishalov.memeory.db.mongo.entity.Channel
import ru.sokomishalov.memeory.db.mongo.mapper.ChannelMapper
import ru.sokomishalov.memeory.db.mongo.repository.ChannelRepository

/**
 * @author sokomishalov
 */
@Service
@Primary
class MongoChannelService(
        private val repository: ChannelRepository,
        private val template: ReactiveMongoTemplate,
        private val channelMapper: ChannelMapper
) : ChannelService {

    override suspend fun findAllEnabled(): List<ChannelDTO> {
        val channels = repository.findAllByEnabled(true).await()
        return channels.map { channelMapper.toDto(it) }
    }

    override suspend fun findAll(): List<ChannelDTO> {
        val channels = repository.findAll().await()
        return channels.map { channelMapper.toDto(it) }
    }

    override suspend fun findById(id: String): ChannelDTO {
        val channel = repository.findById(id).awaitStrict()
        return channelMapper.toDto(channel)
    }

    override suspend fun save(channel: ChannelDTO): ChannelDTO {
        val channelsToSave = channelMapper.toEntity(channel)
        val savedChannel = repository.save(channelsToSave).awaitStrict()
        return channelMapper.toDto(savedChannel)
    }

    override suspend fun saveBatch(vararg batch: ChannelDTO): List<ChannelDTO> {
        val channelsToSave = batch
                .toList()
                .filter { repository.existsById(it.id).awaitStrict().not() }
                .map { channelMapper.toEntity(it) }

        val savedChannels = repository.saveAll(channelsToSave).await()

        return savedChannels.map { channelMapper.toDto(it) }
    }

    override suspend fun toggleEnabled(enabled: Boolean, vararg channelIds: String) {
        val query = query(where(MONGO_ID_FIELD).`in`(*channelIds))
        val update = update("enabled", enabled)
        template.findAndModify<Channel>(query, update, Channel::class.java).await()
    }
}
