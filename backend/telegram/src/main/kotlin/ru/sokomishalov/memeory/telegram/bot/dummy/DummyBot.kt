package ru.sokomishalov.memeory.telegram.bot.dummy

import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Update
import ru.sokomishalov.memeory.core.dto.MemeDTO
import ru.sokomishalov.memeory.telegram.bot.MemeoryBot
import ru.sokomishalov.memeory.telegram.util.api.SendEchoMessage

object DummyBot : MemeoryBot {
    override suspend fun receiveUpdate(update: Update): BotApiMethod<*>? = SendEchoMessage(update)
    override suspend fun broadcastMemes(memes: List<MemeDTO>) = Unit
}