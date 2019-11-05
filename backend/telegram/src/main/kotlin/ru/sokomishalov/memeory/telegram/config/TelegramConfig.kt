package ru.sokomishalov.memeory.telegram.config

import org.springframework.beans.factory.ObjectProvider
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.generics.LongPollingBot
import org.telegram.telegrambots.meta.generics.WebhookBot
import org.telegram.telegrambots.starter.TelegramBotInitializer
import ru.sokomishalov.commons.core.log.Loggable
import ru.sokomishalov.commons.core.string.isNotNullOrBlank
import ru.sokomishalov.memeory.db.BotUserService
import ru.sokomishalov.memeory.telegram.autoconfigure.TelegramBotProperties
import ru.sokomishalov.memeory.telegram.bot.MemeoryBot
import ru.sokomishalov.memeory.telegram.bot.impl.DummyBot
import ru.sokomishalov.memeory.telegram.bot.impl.MemeoryBotImpl

/**
 * @author sokomishalov
 */
@Configuration
@EnableConfigurationProperties(TelegramBotProperties::class)
class TelegramConfig : Loggable {

    @Bean
    fun telegramBotsApi(): TelegramBotsApi = TelegramBotsApi()

    @Bean
    fun telegramBotInitializer(telegramBotsApi: TelegramBotsApi,
                               longPollingBots: ObjectProvider<List<LongPollingBot>>,
                               webHookBots: ObjectProvider<List<WebhookBot>>
    ): TelegramBotInitializer? {
        return runCatching {
            TelegramBotInitializer(telegramBotsApi, longPollingBots.getIfAvailable { emptyList() }, webHookBots.getIfAvailable { emptyList() })
        }.onSuccess {
            log("Bots were initialized successfully")
        }.onFailure {
            logWarn(it)
        }.getOrNull()
    }

    @Bean
    @Primary
    fun memeoryBot(
            props: TelegramBotProperties,
            botUserService: BotUserService
    ): MemeoryBot? {
        return when {
            props.enabled
                    && props.token.isNotNullOrBlank()
                    && props.username.isNotNullOrBlank() -> MemeoryBotImpl(props = props, botUserService = botUserService)
            else -> DummyBot()
        }
    }
}