package ru.sokomishalov.memeory.db

import ru.sokomishalov.memeory.core.dto.MemeDTO
import ru.sokomishalov.memeory.core.dto.MemesPageRequestDTO
import java.time.Duration
import java.time.Duration.ofDays

interface MemeService {

    suspend fun save(batch: List<MemeDTO>, ttl: Duration = ofDays(30)): List<MemeDTO>

    suspend fun getPage(request: MemesPageRequestDTO): List<MemeDTO>

    suspend fun findById(id: String): MemeDTO?
}
