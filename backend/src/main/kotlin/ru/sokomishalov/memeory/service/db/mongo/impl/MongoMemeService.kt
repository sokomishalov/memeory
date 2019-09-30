package ru.sokomishalov.memeory.service.db.mongo.impl

import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
import ru.sokomishalov.commons.core.collections.aFilter
import ru.sokomishalov.commons.core.collections.aForEach
import ru.sokomishalov.commons.core.collections.aMap
import ru.sokomishalov.commons.core.consts.EMPTY
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
@ExperimentalCoroutinesApi
class MongoMemeService(
        private val repository: MemeRepository,
        private val profileService: ProfileService,
        private val template: ReactiveMongoTemplate,
        private val props: MemeoryProperties
) : MemeService {

    override suspend fun saveMemesIfNotExist(memes: List<MemeDTO>): List<MemeDTO> {
        val memesToInsert = memes
                .aFilter { (repository.existsById(it.id)).awaitStrict().not() }
                .aMap { memeMapper.toEntity(it) }

        val savedMemes = repository.saveAll(memesToInsert).await()

        return savedMemes.aMap { memeMapper.toDto(it) }
    }

    override suspend fun pageOfMemes(page: Int, count: Int, token: String?): List<MemeDTO> {
        val id = token ?: EMPTY
        val profile = profileService.findById(id)

        val pageRequest = pageOf(page, count, sortBy(Order(DESC, "publishedAt", NULLS_LAST)))

        val foundMemes = when {
            profile == null || profile.watchAllChannels -> repository.findAllMemesBy(pageRequest).await()
            else -> repository.findAllByChannelIdIn(profile.channels ?: emptyList(), pageRequest).await()
        }

        return foundMemes.aMap { memeMapper.toDto(it) }
    }

    @EventListener(ApplicationReadyEvent::class)
    fun startUp() {
        GlobalScope.launch(Unconfined) {
            val indexes = listOf(
                    Index().on("createdAt", DESC).expire(props.memeLifeTime),
                    Index().on("publishedAt", DESC)
            )
            indexes.aForEach { template.indexOps(Meme::class.java).ensureIndex(it) }
        }
    }
}
