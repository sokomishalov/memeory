package ru.sokomishalov.memeory.db.mongo.impl

import org.springframework.context.annotation.Primary
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction.DESC
import org.springframework.data.domain.Sort.NullHandling.NULLS_LAST
import org.springframework.data.domain.Sort.Order
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.index.Index
import org.springframework.data.mongodb.core.indexOps
import org.springframework.stereotype.Service
import ru.sokomishalov.commons.core.reactor.await
import ru.sokomishalov.commons.core.reactor.awaitStrict
import ru.sokomishalov.commons.core.reactor.awaitUnit
import ru.sokomishalov.commons.core.string.isNotNullOrBlank
import ru.sokomishalov.memeory.core.dto.MemeDTO
import ru.sokomishalov.memeory.core.dto.MemesPageRequestDTO
import ru.sokomishalov.memeory.db.ChannelService
import ru.sokomishalov.memeory.db.MemeService
import ru.sokomishalov.memeory.db.mongo.entity.Meme
import ru.sokomishalov.memeory.db.mongo.mapper.MemeMapper
import ru.sokomishalov.memeory.db.mongo.repository.MemeRepository
import java.time.Duration

@Service
@Primary
class MongoMemeService(
        private val repository: MemeRepository,
        private val channelService: ChannelService,
        private val template: ReactiveMongoTemplate,
        private val memeMapper: MemeMapper
) : MemeService {

    override suspend fun save(batch: List<MemeDTO>, ttl: Duration): List<MemeDTO> {
        ensureIndexes(ttl)
        val memesToInsert = batch
                .filter { (repository.existsById(it.id)).awaitStrict().not() }
                .map { memeMapper.toEntity(it) }

        val savedMemes = repository.saveAll(memesToInsert).await()

        return memeMapper.toDtoList(savedMemes)
    }

    override suspend fun getPage(request: MemesPageRequestDTO): List<MemeDTO> {
        val pageRequest = PageRequest.of(
                request.pageNumber ?: 0,
                request.pageSize ?: Int.MAX_VALUE,
                Sort.by(Order(DESC, "publishedAt", NULLS_LAST))
        )

        val foundMemes = when {

            // paginated memes by specific provider
            request.providerId != null -> {
                val channelIds = channelService
                        .findByProvider(request.providerId!!)
                        .map { it.id }
                repository.findAllByChannelIdIn(channelIds, pageRequest).await()
            }

            // paginated memes by specific topic
            request.topicId.isNotNullOrBlank() -> {
                val channelIds = channelService
                        .findByTopics(request.topicId!!)
                        .map { it.id }
                repository.findAllByChannelIdIn(channelIds, pageRequest).await()
            }

            // paginated memes by specific channel
            request.channelId.isNotNullOrBlank() -> {
                val channelIds = listOf(request.channelId!!)
                repository.findAllByChannelIdIn(channelIds, pageRequest).await()
            }

            // paginated memes from all sources
            else -> repository.findAllMemesBy(pageRequest).await()
        }

        return memeMapper.toDtoList(foundMemes)
    }

    override suspend fun findById(id: String): MemeDTO? {
        return repository.findById(id).await()?.let { memeMapper.toDto(it) }
    }

    private suspend fun ensureIndexes(ttl: Duration) {
        val indexes = listOf(
                Index().on("createdAt", DESC).expire(ttl),
                Index().on("publishedAt", DESC)
        )
        indexes.forEach { template.indexOps<Meme>().ensureIndex(it).awaitUnit() }
    }
}