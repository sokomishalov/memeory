package ru.sokomishalov.memeory.telegram.bot.protocols

import kotlinx.coroutines.runBlocking
import org.telegram.telegrambots.bots.TelegramWebhookBot
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Update
import ru.sokomishalov.memeory.telegram.autoconfigure.TelegramBotProperties
import ru.sokomishalov.memeory.telegram.bot.MemeoryBot
import ru.sokomishalov.memeory.telegram.util.api.SendEchoMessage

class WebHookMemeoryBot(
        private val props: TelegramBotProperties,
        private val bot: MemeoryBot
) : TelegramWebhookBot() {
    override fun getBotUsername(): String = requireNotNull(props.username)
    override fun getBotToken(): String = requireNotNull(props.token)
    override fun getBotPath(): String = requireNotNull(props.path)
    override fun onWebhookUpdateReceived(update: Update): BotApiMethod<*> = runBlocking {
        bot.receiveUpdate(update) ?: SendEchoMessage(update)
    }
}