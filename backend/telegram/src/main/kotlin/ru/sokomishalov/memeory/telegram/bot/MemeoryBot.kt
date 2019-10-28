package ru.sokomishalov.memeory.telegram.bot

import ru.sokomishalov.memeory.core.dto.MemeDTO

interface MemeoryBot {

    suspend fun broadcastBatch(memes: List<MemeDTO>)

}