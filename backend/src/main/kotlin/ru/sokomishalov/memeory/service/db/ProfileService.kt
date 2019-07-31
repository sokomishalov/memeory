package ru.sokomishalov.memeory.service.db

import reactor.core.publisher.Mono
import ru.sokomishalov.memeory.dto.ProfileDTO

interface ProfileService {

    fun findById(id: String): Mono<ProfileDTO>

    fun saveIfNecessary(profile: ProfileDTO): Mono<ProfileDTO>

}
