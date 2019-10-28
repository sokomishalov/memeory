@file:Suppress("unused")

package ru.sokomishalov.memeory.telegram.bot

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update
import ru.sokomishalov.commons.core.log.Loggable
import ru.sokomishalov.commons.core.serialization.OBJECT_MAPPER
import ru.sokomishalov.memeory.db.MemeService
import ru.sokomishalov.memeory.db.ProfileService
import ru.sokomishalov.memeory.telegram.autoconfigure.TelegramBotProperties

class MemeoryBot(
        private val props: TelegramBotProperties,
        private val profileService: ProfileService,
        private val memeService: MemeService
) : TelegramLongPollingBot(), Loggable {

    override fun getBotUsername(): String = props.username ?: throw IllegalArgumentException()
    override fun getBotToken(): String = props.token ?: throw IllegalArgumentException()

    override fun onUpdateReceived(update: Update) {
        log(OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(update))
    }
}