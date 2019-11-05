package ru.sokomishalov.memeory.db

import ru.sokomishalov.memeory.core.dto.ProfileDTO
import ru.sokomishalov.memeory.core.dto.SocialAccountDTO


interface ProfileService {

    suspend fun findById(id: String): ProfileDTO?

    suspend fun update(profile: ProfileDTO): ProfileDTO

    suspend fun saveSocialAccount(id: String?, account: SocialAccountDTO): ProfileDTO?
}
