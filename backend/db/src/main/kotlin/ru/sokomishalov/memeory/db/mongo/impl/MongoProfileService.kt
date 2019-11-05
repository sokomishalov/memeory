package ru.sokomishalov.memeory.db.mongo.impl

import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import ru.sokomishalov.commons.core.reactor.await
import ru.sokomishalov.commons.core.reactor.awaitStrict
import ru.sokomishalov.commons.core.string.isNotNullOrBlank
import ru.sokomishalov.memeory.core.dto.ProfileDTO
import ru.sokomishalov.memeory.core.dto.SocialAccountDTO
import ru.sokomishalov.memeory.core.util.consts.MONGO_KEY_DOT_REPLACEMENT
import ru.sokomishalov.memeory.db.ProfileService
import ru.sokomishalov.memeory.db.mongo.mapper.ProfileMapper
import ru.sokomishalov.memeory.db.mongo.mapper.SocialAccountMapper
import ru.sokomishalov.memeory.db.mongo.repository.ProfileRepository
import ru.sokomishalov.memeory.db.mongo.repository.SocialAccountRepository
import java.util.UUID.randomUUID

/**
 * @author sokomishalov
 */
@Service
@Primary
class MongoProfileService(
        private val repository: ProfileRepository,
        private val profileMapper: ProfileMapper,
        private val socialsRepository: SocialAccountRepository,
        private val socialAccountMapper: SocialAccountMapper
) : ProfileService {

    override suspend fun findById(id: String): ProfileDTO? {
        val profile = repository.findById(id).await()
        return profile?.let { profileMapper.toDto(it) }
    }

    override suspend fun update(profile: ProfileDTO): ProfileDTO {
        require(profile.id.isNotNullOrBlank()) { "You cannot update your account until your authenticate" }
        return saveProfile(profile)
    }

    override suspend fun saveSocials(id: String?, vararg accounts: SocialAccountDTO): ProfileDTO? {
        val socials = accounts
                .map {
                    val toSave = socialAccountMapper.toEntity(it)
                    val savedSocial = socialsRepository.save(toSave).await()

                    it.providerId?.replace(".", MONGO_KEY_DOT_REPLACEMENT) to savedSocial?.uid
                }
                .toMap()

        val profileToSave = when {
            id.isNotNullOrBlank() -> {
                val profile = findById(id)!!
                val newSocialsMap = HashMap(profile.socialsMap).apply { putAll(socials) }
                profile.copy(socialsMap = newSocialsMap)
            }
            else -> ProfileDTO()
        }

        return saveProfile(profileToSave)
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
