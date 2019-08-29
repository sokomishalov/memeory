package ru.sokomishalov.memeory.service.db.mongo

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.springframework.context.annotation.Primary
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.Update.update
import org.springframework.stereotype.Service
import reactor.bool.not
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.entity.mongo.Channel
import ru.sokomishalov.memeory.repository.ChannelRepository
import ru.sokomishalov.memeory.service.db.ChannelService
import ru.sokomishalov.memeory.util.consts.MONGO_ID_FIELD
import ru.sokomishalov.memeory.util.extensions.aFilter
import ru.sokomishalov.memeory.util.extensions.aMap
import ru.sokomishalov.memeory.util.extensions.await
import ru.sokomishalov.memeory.util.extensions.awaitStrict
import ru.sokomishalov.memeory.mapper.ChannelMapper.Companion.INSTANCE as channelMapper

/**
 * @author sokomishalov
 */
@Service
@Primary
@ExperimentalCoroutinesApi
class MongoChannelService(
        private val repository: ChannelRepository,
        private val template: ReactiveMongoTemplate
) : ChannelService {
    override suspend fun findAllEnabled(): List<ChannelDTO> {
        val channels = repository.findAllByEnabled(true).await()
        return channels.aMap { channelMapper.toDto(it) }

    }

    override suspend fun findAll(): List<ChannelDTO> {
        val channels = repository.findAll().await()
        return channels.aMap { channelMapper.toDto(it) }
    }

    override suspend fun findById(channelId: String): ChannelDTO {
        val channel = repository.findById(channelId).awaitStrict()
        return channelMapper.toDto(channel)
    }

    override suspend fun saveOne(channel: ChannelDTO): ChannelDTO {
        val channelsToSave = channelMapper.toEntity(channel)
        val savedChannel = repository.save(channelsToSave).awaitStrict()
        return channelMapper.toDto(savedChannel)
    }

    override suspend fun saveIfNotExist(vararg channels: ChannelDTO): List<ChannelDTO> {
        val channelsToSave = channels
                .aFilter { repository.existsById(it.id).not().awaitStrict() }
                .aMap { channelMapper.toEntity(it) }

        val savedChannels = repository.saveAll(channelsToSave).await()

        return savedChannels.aMap { channelMapper.toDto(it) }
    }

    override suspend fun toggleEnabled(enabled: Boolean, vararg channelIds: String) {
        val query = query(where(MONGO_ID_FIELD).`in`(*channelIds))
        val update = update("enabled", enabled)
        template.findAndModify(query, update, Channel::class.java).await()
    }
}
