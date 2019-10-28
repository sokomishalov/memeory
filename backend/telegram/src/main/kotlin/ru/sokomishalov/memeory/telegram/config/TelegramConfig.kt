package ru.sokomishalov.memeory.telegram.config

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.starter.TelegramBotInitializer
import ru.sokomishalov.commons.core.log.Loggable
import ru.sokomishalov.commons.core.string.isNotNullOrBlank
import ru.sokomishalov.memeory.db.BotUserService
import ru.sokomishalov.memeory.telegram.autoconfigure.TelegramBotProperties
import ru.sokomishalov.memeory.telegram.bot.MemeoryBot
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
    @ConditionalOnBean(TelegramBotsApi::class)
    fun telegramBotInitializer(telegramBotsApi: TelegramBotsApi): TelegramBotInitializer? {
        return runCatching {
            TelegramBotInitializer(telegramBotsApi, emptyList(), emptyList())
        }.onFailure {
            logWarn("Telegram API has not been initialized, bot is off")
        }.onSuccess {
            log("Telegram API has bean initialized successfully")
        }.getOrNull()
    }

    @Bean
    @ConditionalOnBean(TelegramBotInitializer::class)
    fun bot(props: TelegramBotProperties,
            botUserService: BotUserService
    ): MemeoryBot? {
        return when {
            props.token.isNotNullOrBlank() && props.username.isNotNullOrBlank() -> {
                runCatching {
                    MemeoryBotImpl(
                            props = props,
                            botUserService = botUserService
                    )
                }.onSuccess {
                    logInfo("Bot initialized successfully")
                }.getOrNull()
            }
            else -> {
                logWarn("Token and username for bot have not been specified, bot is off")
                null
            }
        }
    }
}