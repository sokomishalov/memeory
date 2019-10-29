package ru.sokomishalov.memeory.telegram.bot

import org.telegram.telegrambots.meta.api.objects.Message
import ru.sokomishalov.memeory.core.dto.MemeDTO

interface MemeoryBot {

    suspend fun receiveMessage(message: Message)

    suspend fun broadcastMemes(memes: List<MemeDTO>)

}