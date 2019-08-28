package ru.sokomishalov.memeory.service.db.mongo

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
import reactor.bool.not
import reactor.core.publisher.Flux
import ru.sokomishalov.memeory.config.MemeoryProperties
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.entity.mongo.Meme
import ru.sokomishalov.memeory.repository.MemeRepository
import ru.sokomishalov.memeory.service.db.MemeService
import ru.sokomishalov.memeory.service.db.ProfileService
import ru.sokomishalov.memeory.util.consts.EMPTY
import ru.sokomishalov.memeory.util.extensions.*
import java.time.Duration.ofDays
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

    override fun saveMemesIfNotExist(memes: Flux<MemeDTO>): Flux<MemeDTO> = flux(Unconfined) {
        val memesToInsert = memes
                .await()
                .aFilter { (!repository.existsById(it.id)).awaitStrict() }
                .aMap { memeMapper.toEntity(it) }

        val savedMemes = repository
                .saveAll(memesToInsert)
                .await()

        savedMemes
                .aMap { memeMapper.toDto(it) }
                .aForEach { send(it) }
    }

    override fun pageOfMemes(page: Int, count: Int, token: String?): Flux<MemeDTO> = flux(Unconfined) {
        val id = token ?: EMPTY
        val profile = profileService.findById(id).await()

        val pageRequest = pageOf(page, count, sortBy(Order(DESC, "publishedAt", NULLS_LAST)))

        val foundMemes = when {
            profile == null || profile.watchAllChannels -> repository.findAllMemesBy(pageRequest).await()
            else -> repository.findAllByChannelIdIn(profile.channels ?: emptyList(), pageRequest).await()
        }

        foundMemes
                .aMap { memeMapper.toDto(it) }
                .aForEach { send(it) }
    }

    @EventListener(ApplicationReadyEvent::class)
    fun startUp() {
        GlobalScope.launch(Unconfined) {
            val indexes = listOf(
                    Index().on("createdAt", DESC).expire(ofDays(props.memeExpirationDays.toLong())),
                    Index().on("publishedAt", DESC)
            )
            indexes.aForEach { template.indexOps(Meme::class.java).ensureIndex(it) }
        }
    }
}
