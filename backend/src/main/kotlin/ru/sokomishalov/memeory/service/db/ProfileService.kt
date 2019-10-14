package ru.sokomishalov.memeory.service.db

import ru.sokomishalov.memeory.dto.ProfileDTO

interface ProfileService {

    suspend fun findById(id: String): ProfileDTO?

    suspend fun save(profile: ProfileDTO): ProfileDTO

}
