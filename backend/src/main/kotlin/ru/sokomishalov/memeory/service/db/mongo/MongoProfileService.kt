package ru.sokomishalov.memeory.service.db.mongo

import org.springframework.context.annotation.Primary
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.sokomishalov.commons.core.consts.EMPTY
import ru.sokomishalov.commons.core.log.Loggable
import ru.sokomishalov.commons.core.maps.isNotNullOrEmpty
import ru.sokomishalov.commons.core.reactor.await
import ru.sokomishalov.commons.core.reactor.awaitStrict
import ru.sokomishalov.memeory.dto.ProfileDTO
import ru.sokomishalov.memeory.entity.mongo.Profile
import ru.sokomishalov.memeory.repository.ProfileRepository
import ru.sokomishalov.memeory.service.db.ProfileService
import java.util.UUID.randomUUID
import org.springframework.data.mongodb.core.query.Criteria.where as criteriaWhere
import ru.sokomishalov.memeory.mapper.ProfileMapper.Companion.INSTANCE as profileMapper


/**
 * @author sokomishalov
 */
@Service
@Primary
class MongoProfileService(
        private val repository: ProfileRepository,
        private val template: ReactiveMongoTemplate
) : ProfileService, Loggable {

    override suspend fun findById(id: String): ProfileDTO? {
        val profile = repository.findById(id).await()
        return profile?.let { profileMapper.toDto(it) }
    }

    @Transactional
    override suspend fun saveIfNecessary(profile: ProfileDTO): ProfileDTO {
        return when {
            profile.id.isNullOrBlank() && profile.socialsMap.isNotNullOrEmpty() -> {
                val criteriaList = profile.socialsMap.entries.map {
                    criteriaWhere("socialsMap.${it.key}")
                            .exists(true)
                            .and("socialsMap.${it.key}.id")
                            .`is`(it.value.getOrDefault("id", EMPTY))
                }

                val query = Query(Criteria().orOperator(*criteriaList.toTypedArray()))

                val profiles = template.find(query, Profile::class.java).await()

                when {
                    profiles.isNullOrEmpty() -> saveProfile(profile)
                    else -> profileMapper.toDto(profiles.first())
                }
            }

            else -> saveProfile(profile)
        }
    }

    suspend fun saveProfile(profile: ProfileDTO): ProfileDTO {
        if (profile.id.isNullOrBlank()) {
            profile.id = randomUUID().toString()
        }
        val toSave = profileMapper.toEntity(profile)
        val savedProfile = repository.save(toSave).awaitStrict()
        return profileMapper.toDto(savedProfile)
    }
}
