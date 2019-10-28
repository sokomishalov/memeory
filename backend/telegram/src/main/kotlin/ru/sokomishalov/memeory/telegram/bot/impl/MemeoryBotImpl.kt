@file:Suppress("unused")

package ru.sokomishalov.memeory.telegram.bot.impl

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update
import ru.sokomishalov.commons.core.common.unit
import ru.sokomishalov.commons.core.log.Loggable
import ru.sokomishalov.commons.core.serialization.OBJECT_MAPPER
import ru.sokomishalov.memeory.core.dto.BotUserDTO
import ru.sokomishalov.memeory.core.dto.MemeDTO
import ru.sokomishalov.memeory.db.MemeService
import ru.sokomishalov.memeory.db.ProfileService
import ru.sokomishalov.memeory.telegram.autoconfigure.TelegramBotProperties
import ru.sokomishalov.memeory.telegram.bot.MemeoryBot

class MemeoryBotImpl(
        private val props: TelegramBotProperties,
        private val profileService: ProfileService,
        private val memeService: MemeService
) : TelegramLongPollingBot(), Loggable, MemeoryBot {

    override fun getBotUsername(): String = props.username ?: throw IllegalArgumentException()
    override fun getBotToken(): String = props.token ?: throw IllegalArgumentException()

    override fun onUpdateReceived(update: Update) {
        log(OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(update))
    }

    override suspend fun broadcastBatch(memes: List<MemeDTO>) {
        runCatching {
            log("${memes.size} broadcasted")
        }.getOrNull().unit()
    }


    private fun Update.extractUserInfo(): BotUserDTO {
        val from = message.from
        return BotUserDTO(
                id = from.id.toLong(),
                username = from.userName,
                fullName = "${from.lastName} ${from.firstName}",
                languageCode = from.languageCode,
                chatId = message.chatId
        )
    }
}