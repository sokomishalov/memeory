package ru.sokomishalov.memeory.db.mongo.impl

import org.springframework.context.annotation.Primary
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import ru.sokomishalov.commons.core.collections.isNotNullOrEmpty
import ru.sokomishalov.commons.core.reactor.await
import ru.sokomishalov.commons.core.reactor.awaitStrict
import ru.sokomishalov.memeory.core.dto.ProfileDTO
import ru.sokomishalov.memeory.core.util.consts.MONGO_KEY_DOT_REPLACEMENT
import ru.sokomishalov.memeory.db.ProfileService
import ru.sokomishalov.memeory.db.mongo.entity.Profile
import ru.sokomishalov.memeory.db.mongo.repository.ProfileRepository
import java.util.UUID.randomUUID
import org.springframework.data.mongodb.core.query.Criteria.where as criteriaWhere
import ru.sokomishalov.memeory.db.mongo.mapper.ProfileMapper.Companion.INSTANCE as profileMapper


/**
 * @author sokomishalov
 */
@Service
@Primary
class MongoProfileService(
        private val repository: ProfileRepository,
        private val template: ReactiveMongoTemplate
) : ProfileService {

    override suspend fun findById(id: String): ProfileDTO? {
        val profile = repository.findById(id).await()
        return profile?.let { profileMapper.toDto(it) }
    }

    override suspend fun save(profile: ProfileDTO): ProfileDTO {
        return when {
            profile.id.isNullOrBlank() && profile.socialsMap.isNotNullOrEmpty() -> {
                val criteriaList = profile.socialsMap.entries.map {
                    val key = it.key.replace(".", MONGO_KEY_DOT_REPLACEMENT)
                    criteriaWhere("socialsMap.$key")
                            .exists(true)
                            .and("socialsMap.$key.email")
                            .`is`(it.value.email)
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

    private suspend fun saveProfile(profile: ProfileDTO): ProfileDTO {
        if (profile.id.isNullOrBlank()) {
            profile.id = randomUUID().toString()
        }
        val toSave = profileMapper.toEntity(profile)
        val savedProfile = repository.save(toSave).awaitStrict()
        return profileMapper.toDto(savedProfile)
    }
}
