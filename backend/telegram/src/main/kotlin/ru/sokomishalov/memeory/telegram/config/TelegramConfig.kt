package ru.sokomishalov.memeory.telegram.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
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
import ru.sokomishalov.memeory.telegram.bot.dummy.DummyBot
import ru.sokomishalov.memeory.telegram.bot.impl.MemeoryBotImpl

/**
 * @author sokomishalov
 */
@Configuration
@EnableConfigurationProperties(TelegramBotProperties::class)
class TelegramConfig : Loggable {

    @Bean
    fun telegramBotsApi(): TelegramBotsApi = TelegramBotsApi()

    @Bean("bot")
    @Primary
    fun memeoryBot(props: TelegramBotProperties, botUserService: BotUserService): MemeoryBot {
        return when {
            props.enabled
                    && props.token.isNotNullOrBlank()
                    && props.username.isNotNullOrBlank() -> MemeoryBotImpl(props = props, botUserService = botUserService)
            else -> DummyBot
        }
    }

    @Bean
    @DependsOn("bot")
    fun telegramBotInitializer(telegramBotsApi: TelegramBotsApi, bot: MemeoryBot): TelegramBotInitializer? {
        return runCatching {
            when (bot) {
                is LongPollingBot -> TelegramBotInitializer(telegramBotsApi, listOf(bot), emptyList())
                is WebhookBot -> TelegramBotInitializer(telegramBotsApi, emptyList(), listOf(bot))
                else -> {
                    logWarn("Not bots found")
                    TelegramBotInitializer(telegramBotsApi, emptyList(), emptyList())
                }
            }
        }.onFailure {
            logWarn(it)
        }.getOrNull()
    }
}