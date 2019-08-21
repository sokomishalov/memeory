package ru.sokomishalov.memeory.service.db.mongo.coroutine

import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.reactor.flux
import kotlinx.coroutines.reactor.mono
import org.springframework.context.annotation.Primary
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.Update.update
import org.springframework.stereotype.Service
import reactor.bool.not
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.sokomishalov.memeory.condition.ConditionalOnUsingCoroutines
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.entity.mongo.Channel
import ru.sokomishalov.memeory.repository.ChannelRepository
import ru.sokomishalov.memeory.service.db.ChannelService
import ru.sokomishalov.memeory.util.MONGO_ID_FIELD
import ru.sokomishalov.memeory.util.extensions.*
import ru.sokomishalov.memeory.mapper.ChannelMapper.Companion.INSTANCE as channelMapper

/**
 * @author sokomishalov
 */
@Service
@Primary
@ConditionalOnUsingCoroutines
class CoroutineMongoChannelService(
        private val repository: ChannelRepository,
        private val template: ReactiveMongoTemplate
) : ChannelService {
    override fun findAllEnabled(): Flux<ChannelDTO> = GlobalScope.flux(Unconfined) {
        repository
                .findAllByEnabled(true)
                .await()
                .aMap { channelMapper.toDto(it) }
                .aForEach { send(it) }
    }

    override fun findAll(): Flux<ChannelDTO> = GlobalScope.flux(Unconfined) {
        repository
                .findAll()
                .await()
                .aMap { channelMapper.toDto(it) }
                .aForEach { send(it) }
    }

    override fun findById(channelId: String): Mono<ChannelDTO> = GlobalScope.mono(Unconfined) {
        val channel = repository.findById(channelId).awaitStrict()
        channelMapper.toDto(channel)
    }

    override fun saveOne(channel: ChannelDTO): Mono<ChannelDTO> = GlobalScope.mono(Unconfined) {
        val channelsToSave = channelMapper.toEntity(channel)
        val savedChannel = repository.save(channelsToSave).awaitStrict()
        channelMapper.toDto(savedChannel)
    }

    override fun saveIfNotExist(vararg channels: ChannelDTO): Flux<ChannelDTO> = GlobalScope.flux(Unconfined) {
        val channelsToSave = channels
                .aFilter { repository.existsById(it.id).not().awaitStrict() }
                .aMap { channelMapper.toEntity(it) }

        repository
                .saveAll(channelsToSave)
                .await()
                .aMap { channelMapper.toDto(it) }
                .aForEach { send(it) }
    }

    override fun toggleEnabled(enabled: Boolean, vararg channelIds: String): Mono<Unit> = GlobalScope.mono(Unconfined) {
        val query = query(where(MONGO_ID_FIELD).`in`(*channelIds))
        val update = update("enabled", enabled)
        template.findAndModify(query, update, Channel::class.java).awaitUnit()
    }
}
