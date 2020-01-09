package ru.sokomishalov.memeory.telegram.bot

import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Update
import ru.sokomishalov.memeory.core.dto.MemeDTO

interface MemeoryBot {

    suspend fun receiveUpdate(update: Update) : BotApiMethod<*>

    suspend fun broadcastMemes(memes: List<MemeDTO>)

}