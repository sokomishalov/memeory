package ru.sokomishalov.memeory.service.db.mongo

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import ru.sokomishalov.memeory.dto.ProfileDTO
import ru.sokomishalov.memeory.repository.ProfileRepository
import ru.sokomishalov.memeory.service.db.ProfileService
import java.util.UUID.randomUUID
import ru.sokomishalov.memeory.mapper.ProfileMapper.Companion.INSTANCE as profileMapper

@Service
class MongoProfileService(
        private val repository: ProfileRepository
) : ProfileService {

    // fixme improve
    @Transactional
    override fun saveProfileInfo(profile: ProfileDTO): Mono<ProfileDTO> {
        return just(profile)
                .doOnNext {
                    if (it.id.isNullOrBlank()) {
                        it.id = randomUUID().toString()
                    }
                }
                .map { profileMapper.toEntity(it) }
                .flatMap { repository.save(it) }
                .map { profileMapper.toDto(it) }
    }
}
