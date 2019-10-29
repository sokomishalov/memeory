package ru.sokomishalov.memeory.telegram.bot.impl

import ru.sokomishalov.commons.core.common.unit
import ru.sokomishalov.memeory.core.dto.MemeDTO
import ru.sokomishalov.memeory.telegram.bot.MemeoryBot

class DummyBot : MemeoryBot {
    override suspend fun broadcastMemes(memes: List<MemeDTO>) = unit()
}