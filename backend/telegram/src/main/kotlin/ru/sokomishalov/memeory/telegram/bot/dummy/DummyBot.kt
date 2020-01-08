package ru.sokomishalov.memeory.telegram.bot.dummy

import org.telegram.telegrambots.meta.api.objects.Update
import ru.sokomishalov.memeory.core.dto.MemeDTO
import ru.sokomishalov.memeory.telegram.bot.MemeoryBot

object DummyBot : MemeoryBot {
    override suspend fun receiveUpdate(update: Update): Unit = Unit
    override suspend fun broadcastMemes(memes: List<MemeDTO>) = Unit
}