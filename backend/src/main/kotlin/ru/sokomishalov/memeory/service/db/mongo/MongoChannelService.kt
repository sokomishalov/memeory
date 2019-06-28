package ru.sokomishalov.memeory.service.db.mongo

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.data.domain.Sort.Direction.DESC
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.index.Index
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.Update.update
import org.springframework.stereotype.Service
import reactor.bool.not
import reactor.core.publisher.Flux
import reactor.core.publisher.Flux.fromArray
import reactor.core.publisher.Flux.fromIterable
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import reactor.util.function.Tuples.of
import ru.sokomishalov.memeory.config.props.MemeoryProperties
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.entity.Channel
import ru.sokomishalov.memeory.entity.Meme
import ru.sokomishalov.memeory.repository.ChannelRepository
import ru.sokomishalov.memeory.service.db.ChannelService
import ru.sokomishalov.memeory.util.MONGO_ID_FIELD
import java.time.Duration.ofDays
import ru.sokomishalov.memeory.mapper.ChannelMapper.Companion.INSTANCE as channelMapper


/**
 * @author sokomishalov
 */
@Service
class MongoChannelService(private val repository: ChannelRepository,
                          private val reactiveMongoTemplate: ReactiveMongoTemplate,
                          private val props: MemeoryProperties
) : ChannelService {
    override fun findAllEnabled(): Flux<ChannelDTO> {
        return repository
                .findAll()
                .filter { it.enabled }
                .map { channelMapper.toDto(it) }
    }

    override fun findAll(): Flux<ChannelDTO> {
        return repository
                .findAll()
                .map { channelMapper.toDto(it) }
    }

    override fun findById(channelId: String): Mono<ChannelDTO> {
        return repository
                .findById(channelId)
                .map { channelMapper.toDto(it) }
    }

    override fun saveOne(channel: ChannelDTO): Mono<ChannelDTO> {
        return just(channel)
                .map { channelMapper.toEntity(it) }
                .flatMap { repository.save(it) }
                .map { channelMapper.toDto(it) }

    }

    override fun saveIfNotExist(vararg channels: ChannelDTO): Flux<ChannelDTO> {
        return fromArray(channels)
                .filterWhen { repository.existsById(it.id).not() }
                .map { channelMapper.toEntity(it) }
                .let { repository.saveAll(it) }
                .map { channelMapper.toDto(it) }
    }

    override fun toggleEnabled(enabled: Boolean, vararg channelIds: String): Mono<Void> {
        return fromArray(channelIds)
                .map { query(where(MONGO_ID_FIELD).`in`(*channelIds)) }
                .map { of(it, update("enabled", enabled)) }
                .flatMap { reactiveMongoTemplate.findAndModify(it.t1, it.t2, Channel::class.java) }
                .then()
    }

    @EventListener(ApplicationReadyEvent::class)
    fun startUp() {
        fromIterable(
                listOf(
                        Index().on("createdAt", DESC).expire(ofDays(props.memeExpirationDays.toLong())),
                        Index().on("publishedAt", DESC)
                ))
                .flatMap {
                    reactiveMongoTemplate
                            .indexOps(Meme::class.java)
                            .ensureIndex(it)
                }
                .subscribe()
    }
}
