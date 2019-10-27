package ru.sokomishalov.memeory.db

import ru.sokomishalov.memeory.core.dto.ProfileDTO


interface ProfileService {

    suspend fun findById(id: String): ProfileDTO?

    suspend fun save(profile: ProfileDTO): ProfileDTO

}
