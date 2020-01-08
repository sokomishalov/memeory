package ru.sokomishalov.memeory.telegram.bot

import org.telegram.telegrambots.meta.api.objects.Update
import ru.sokomishalov.memeory.core.dto.MemeDTO

interface MemeoryBot {

    suspend fun receiveUpdate(update: Update)

    suspend fun broadcastMemes(memes: List<MemeDTO>)

}