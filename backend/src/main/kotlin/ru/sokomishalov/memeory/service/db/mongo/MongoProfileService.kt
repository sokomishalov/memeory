package ru.sokomishalov.memeory.service.db.mongo

import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import ru.sokomishalov.memeory.dto.ProfileDTO
import ru.sokomishalov.memeory.repository.ProfileRepository
import ru.sokomishalov.memeory.service.db.ProfileService
import ru.sokomishalov.memeory.util.containsEntryFrom
import java.util.UUID.randomUUID
import ru.sokomishalov.memeory.mapper.ProfileMapper.Companion.INSTANCE as profileMapper

@Service
class MongoProfileService(
        private val repository: ProfileRepository,
        @Suppress("unused")
        private val template: ReactiveMongoTemplate
) : ProfileService {

    @Transactional
    override fun saveProfileInfo(profile: ProfileDTO): Mono<ProfileDTO> {
        return just(profile)
                .flatMap { p ->
                    when {
                        p.id.isNullOrBlank() && p.socialsMap.isNullOrEmpty().not() ->
                            repository
                                    .findAll()
                                    .filter { it.socialsMap.containsEntryFrom(p.socialsMap) }
                                    .next()
                                    .map { profileMapper.toDto(it) }
                                    .defaultIfEmpty(p)
                        else ->
                            just(p)
                    }
                }
                .doOnNext {
                    when {
                        it.id.isNullOrBlank() -> it.apply { id = randomUUID().toString() }
                    }
                }
                .map { profileMapper.toEntity(it) }
                .flatMap { repository.save(it) }
                .map { profileMapper.toDto(it) }
    }
}
