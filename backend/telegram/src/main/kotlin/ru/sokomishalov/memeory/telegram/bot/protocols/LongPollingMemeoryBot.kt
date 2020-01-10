package ru.sokomishalov.memeory.telegram.bot.protocols

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update
import ru.sokomishalov.commons.core.common.unit
import ru.sokomishalov.memeory.telegram.autoconfigure.TelegramBotProperties
import ru.sokomishalov.memeory.telegram.bot.MemeoryBot
import ru.sokomishalov.memeory.telegram.util.api.send

class LongPollingMemeoryBot(
        private val props: TelegramBotProperties,
        private val bot: MemeoryBot
) : TelegramLongPollingBot() {
    override fun getBotUsername(): String = requireNotNull(props.username)
    override fun getBotToken(): String = requireNotNull(props.token)
    override fun onUpdateReceived(update: Update) = GlobalScope.launch { send(bot.receiveUpdate(update)) }.unit()
}