package ru.sokomishalov.memeory.service.db.mongo

import org.slf4j.Logger
import org.springframework.data.domain.Sort.Direction.DESC
import org.springframework.data.domain.Sort.NullHandling.NULLS_LAST
import org.springframework.data.domain.Sort.Order
import org.springframework.stereotype.Service
import reactor.bool.not
import reactor.core.publisher.Flux
import reactor.core.publisher.Flux.empty
import reactor.core.publisher.Mono.justOrEmpty
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.repository.MemeRepository
import ru.sokomishalov.memeory.repository.ProfileRepository
import ru.sokomishalov.memeory.service.db.MemeService
import ru.sokomishalov.memeory.util.EMPTY
import ru.sokomishalov.memeory.util.loggerFor
import org.springframework.data.domain.PageRequest.of as pageOf
import org.springframework.data.domain.Sort.by as sortBy
import ru.sokomishalov.memeory.mapper.MemeMapper.Companion.INSTANCE as memeMapper

@Service
class MongoMemeService(
        private val repository: MemeRepository,
        private val profileRepository: ProfileRepository
) : MemeService {

    companion object {
        private val log: Logger = loggerFor(MongoMemeService::class.java)
    }

    override fun saveMemesIfNotExist(memes: Flux<MemeDTO>): Flux<MemeDTO> {
        return memes
                .filterWhen { repository.existsById(it.id).not() }
                .map { memeMapper.toEntity(it) }
                .let { repository.saveAll(it) }
                .map { memeMapper.toDto(it) }

    }

    override fun pageOfMemes(page: Int, count: Int, token: String?): Flux<MemeDTO> {
        return justOrEmpty(token)
                .defaultIfEmpty(EMPTY)
                .flatMap { profileRepository.findById(it) }
                .flatMapMany {
                    val pageRequest = pageOf(page, count, sortBy(Order(DESC, "publishedAt", NULLS_LAST)))

                    if (it.watchAllChannels) {
                        repository.findAllMemesBy(pageRequest)
                    } else {
                        repository.findAllByChannelIdIn(it.channels ?: emptyList(), pageRequest)
                    }
                }
                .map { memeMapper.toDto(it) }
                .onErrorResume {
                    log.error(it.message, it)
                    empty()
                }
    }
}
