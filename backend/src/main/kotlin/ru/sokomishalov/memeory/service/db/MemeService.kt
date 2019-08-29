package ru.sokomishalov.memeory.service.db

import ru.sokomishalov.memeory.dto.MemeDTO

interface MemeService {

    suspend fun saveMemesIfNotExist(memes: List<MemeDTO>): List<MemeDTO>

    suspend fun pageOfMemes(page: Int, count: Int, token: String?): List<MemeDTO>
}
