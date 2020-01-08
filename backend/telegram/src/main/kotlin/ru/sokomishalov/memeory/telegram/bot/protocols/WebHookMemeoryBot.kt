package ru.sokomishalov.memeory.telegram.bot.protocols

import kotlinx.coroutines.runBlocking
import org.telegram.telegrambots.bots.TelegramWebhookBot
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.GetMe
import org.telegram.telegrambots.meta.api.objects.Update
import ru.sokomishalov.memeory.telegram.autoconfigure.TelegramBotProperties
import ru.sokomishalov.memeory.telegram.bot.MemeoryBot

class WebHookMemeoryBot(
        private val props: TelegramBotProperties,
        private val bot: MemeoryBot
) : TelegramWebhookBot() {
    override fun getBotUsername(): String = requireNotNull(props.username)
    override fun getBotToken(): String = requireNotNull(props.username)
    override fun getBotPath(): String = requireNotNull(props.path)

    override fun onWebhookUpdateReceived(update: Update): BotApiMethod<*> {
        return runBlocking {
            bot.receiveUpdate(update)
            GetMe()
        }
    }
}