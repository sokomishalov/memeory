package ru.sokomishalov.memeory.telegram.autoconfigure

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

/**
 * @author sokomishalov
 */
@ConfigurationProperties(prefix = "memeory.telegram.bot")
@ConstructorBinding
data class TelegramBotProperties(
        val enabled: Boolean = true,
        val username: String? = null,
        val token: String? = null
)