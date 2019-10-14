package ru.sokomishalov.memeory.service.db

import ru.sokomishalov.memeory.dto.MemeDTO

interface MemeService {

    suspend fun saveBatch(batch: List<MemeDTO>): List<MemeDTO>

    suspend fun getPage(page: Int, count: Int, token: String?): List<MemeDTO>
}
