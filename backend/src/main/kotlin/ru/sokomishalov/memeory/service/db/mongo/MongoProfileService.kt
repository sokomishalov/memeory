package ru.sokomishalov.memeory.service.db.mongo

import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import ru.sokomishalov.memeory.dto.ProfileDTO
import ru.sokomishalov.memeory.entity.mongo.Profile
import ru.sokomishalov.memeory.repository.ProfileRepository
import ru.sokomishalov.memeory.service.db.ProfileService
import ru.sokomishalov.memeory.util.isNotNullOrBlank
import ru.sokomishalov.memeory.util.isNotNullOrEmpty
import java.util.UUID.randomUUID
import org.springframework.data.mongodb.core.query.Criteria.where as criteriaWhere
import reactor.core.publisher.Flux.fromIterable as fluxFromIterable
import reactor.core.publisher.Mono.just as monoJust
import ru.sokomishalov.memeory.mapper.ProfileMapper.Companion.INSTANCE as profileMapper

@Service
class MongoProfileService(
        private val repository: ProfileRepository,
        @Suppress("unused")
        private val template: ReactiveMongoTemplate
) : ProfileService {

    @Transactional
    override fun saveProfileInfoIfNecessary(profile: ProfileDTO): Mono<ProfileDTO> {
        return monoJust(profile)
                .flatMap { p ->
                    when {
                        p.id.isNullOrBlank() && p.socialsMap.isNotNullOrEmpty() ->
                            fluxFromIterable(p.socialsMap.entries)
                                    .map {
                                        criteriaWhere("socialsMap.${it.key}")
                                                .exists(true)
                                                .and("socialsMap.${it.key}.id")
                                                .`is`(it.value["id"])
                                    }
                                    .collectList()
                                    .map { Criteria().orOperator(*it.toTypedArray()) }
                                    .map { Query(it) }
                                    .flatMap { template.findOne(it, Profile::class.java) }
                                    .map { profileMapper.toDto(it) }
                                    .defaultIfEmpty(p)

                        p.id.isNotNullOrBlank() ->
                            saveProfile(p)

                        else ->
                            monoJust(p)
                                    .doOnNext { it.id = randomUUID().toString() }
                                    .flatMap { saveProfile(it) }
                    }
                }
    }

    private fun saveProfile(profile: ProfileDTO): Mono<ProfileDTO> {
        return monoJust(profile)
                .map { profileMapper.toEntity(it) }
                .flatMap { repository.save(it) }
                .map { profileMapper.toDto(it) }
    }
}
