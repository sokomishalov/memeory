package ru.sokomishalov.memeory.telegram.bot.dummy

import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import ru.sokomishalov.memeory.core.dto.MemeDTO
import ru.sokomishalov.memeory.telegram.bot.MemeoryBot

object DummyBot : MemeoryBot {
    override suspend fun receiveUpdate(update: Update): BotApiMethod<*> = SendMessage(update.message.chatId, update.message.text)
    override suspend fun broadcastMemes(memes: List<MemeDTO>) = Unit
}