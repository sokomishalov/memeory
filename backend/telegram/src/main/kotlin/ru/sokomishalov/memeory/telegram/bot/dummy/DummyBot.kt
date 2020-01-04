package ru.sokomishalov.memeory.telegram.bot.dummy

import org.telegram.telegrambots.meta.api.objects.Message
import ru.sokomishalov.commons.core.common.unit
import ru.sokomishalov.memeory.core.dto.MemeDTO
import ru.sokomishalov.memeory.telegram.bot.MemeoryBot

object DummyBot : MemeoryBot {
    override suspend fun receiveMessage(message: Message) = unit()
    override suspend fun broadcastMemes(memes: List<MemeDTO>) = unit()
}