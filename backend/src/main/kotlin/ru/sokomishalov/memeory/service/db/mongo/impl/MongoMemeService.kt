package ru.sokomishalov.memeory.service.db.mongo.impl

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Primary
import org.springframework.context.event.EventListener
import org.springframework.data.domain.Sort.Direction.DESC
import org.springframework.data.domain.Sort.NullHandling.NULLS_LAST
import org.springframework.data.domain.Sort.Order
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.index.Index
import org.springframework.stereotype.Service
import ru.sokomishalov.commons.core.collections.aMap
import ru.sokomishalov.commons.core.consts.EMPTY
import ru.sokomishalov.commons.core.log.Loggable
import ru.sokomishalov.commons.core.reactor.await
import ru.sokomishalov.commons.core.reactor.awaitStrict
import ru.sokomishalov.memeory.autoconfigure.MemeoryProperties
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.service.db.MemeService
import ru.sokomishalov.memeory.service.db.ProfileService
import ru.sokomishalov.memeory.service.db.mongo.entity.Meme
import ru.sokomishalov.memeory.service.db.mongo.repository.MemeRepository
import org.springframework.data.domain.PageRequest.of as pageOf
import org.springframework.data.domain.Sort.by as sortBy
import ru.sokomishalov.memeory.mapper.MemeMapper.Companion.INSTANCE as memeMapper

@Service
@Primary
class MongoMemeService(
        private val repository: MemeRepository,
        private val profileService: ProfileService,
        private val template: ReactiveMongoTemplate,
        private val props: MemeoryProperties
) : MemeService, Loggable {

    override suspend fun saveBatch(batch: List<MemeDTO>): List<MemeDTO> {
        val memesToInsert = batch
                .filter { (repository.existsById(it.id)).awaitStrict().not() }
                .map { memeMapper.toEntity(it) }

        val savedMemes = repository.saveAll(memesToInsert).await()

        return savedMemes.map { memeMapper.toDto(it) }
    }

    override suspend fun getPage(page: Int, count: Int, token: String?): List<MemeDTO> {
        val id = token ?: EMPTY
        val profile = profileService.findById(id)

        val pageRequest = pageOf(page, count, sortBy(Order(DESC, "publishedAt", NULLS_LAST)))

        val foundMemes = when {
            profile == null || profile.watchAllChannels -> repository.findAllMemesBy(pageRequest).await()
            else -> repository.findAllByChannelIdIn(profile.channels, pageRequest).await()
        }

        return foundMemes.aMap { memeMapper.toDto(it) }
    }

    @EventListener(ApplicationReadyEvent::class)
    fun startUp() {
        GlobalScope.launch {
            val indexes = listOf(
                    Index().on("createdAt", DESC).expire(props.memeLifeTime),
                    Index().on("publishedAt", DESC)
            )
            indexes.forEach { template.indexOps(Meme::class.java).ensureIndex(it) }
        }
    }
}
