package ru.sokomishalov.memeory.telegram.config

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.telegram.telegrambots.meta.generics.LongPollingBot
import org.telegram.telegrambots.meta.generics.WebhookBot
import ru.sokomishalov.commons.core.log.Loggable
import ru.sokomishalov.memeory.db.BotUserService
import ru.sokomishalov.memeory.db.TopicService
import ru.sokomishalov.memeory.telegram.autoconfigure.TelegramBotProperties
import ru.sokomishalov.memeory.telegram.bot.MemeoryBot
import ru.sokomishalov.memeory.telegram.bot.dummy.DummyBot
import ru.sokomishalov.memeory.telegram.bot.impl.MemeoryBotImpl
import ru.sokomishalov.memeory.telegram.bot.protocols.LongPollingMemeoryBot
import ru.sokomishalov.memeory.telegram.bot.protocols.WebHookMemeoryBot

/**
 * @author sokomishalov
 */
@Configuration
@EnableConfigurationProperties(TelegramBotProperties::class)
class TelegramConfig {

    companion object : Loggable

    @Bean
    @Primary
    @ConditionalOnProperty(prefix = "memeory.telegram.bot", value = ["enabled"], havingValue = "true", matchIfMissing = false)
    fun memeoryBot(props: TelegramBotProperties, botUserService: BotUserService, topicService: TopicService): MemeoryBot {
        return MemeoryBotImpl(props = props, botUserService = botUserService, topicService = topicService)
    }

    @Bean
    @ConditionalOnMissingBean(MemeoryBot::class)
    fun dummyBot(): MemeoryBot {
        return DummyBot
    }

    @Bean
    @ConditionalOnProperty(prefix = "memeory.telegram.bot", value = ["enabled"], havingValue = "true", matchIfMissing = false)
    fun longPollingBot(props: TelegramBotProperties, memeoryBot: MemeoryBot): LongPollingBot? {
        return LongPollingMemeoryBot(props, memeoryBot)
    }

    @Bean
    @ConditionalOnProperty(prefix = "memeory.telegram.bot", value = ["enabled", "path"], matchIfMissing = false)
    fun webHookBot(props: TelegramBotProperties, memeoryBot: MemeoryBot): WebhookBot {
        return WebHookMemeoryBot(props, memeoryBot)
    }
}