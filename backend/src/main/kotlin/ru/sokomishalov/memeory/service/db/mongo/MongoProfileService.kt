package ru.sokomishalov.memeory.service.db.mongo

import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux.fromIterable
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import ru.sokomishalov.memeory.dto.ProfileDTO
import ru.sokomishalov.memeory.entity.mongo.Profile
import ru.sokomishalov.memeory.repository.ProfileRepository
import ru.sokomishalov.memeory.service.db.ProfileService
import java.util.UUID.randomUUID
import org.springframework.data.mongodb.core.query.Criteria.where as criteriaWhere
import ru.sokomishalov.memeory.mapper.ProfileMapper.Companion.INSTANCE as profileMapper

@Service
class MongoProfileService(
        private val repository: ProfileRepository,
        @Suppress("unused")
        private val template: ReactiveMongoTemplate
) : ProfileService {

    @Transactional
    override fun saveProfileInfoIfNecessary(profile: ProfileDTO): Mono<ProfileDTO> {

        return just(profile)
                .flatMap { p ->
                    when {
                        p.id.isNullOrBlank() && p.socialsMap.isNullOrEmpty().not() -> {
                            fromIterable(p.socialsMap.entries)
                                    .map { criteriaWhere("socialsMap.${it.key}").exists(true).and("socialsMap.${it.key}.id").`is`(it.value["id"]) }
                                    .collectList()
                                    .map { Criteria().orOperator(*it.toTypedArray()) }
                                    .map { Query(it) }
                                    .flatMap { template.findOne(it, Profile::class.java) }
                                    .map { profileMapper.toDto(it) }
                                    .defaultIfEmpty(p)
                        }
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
