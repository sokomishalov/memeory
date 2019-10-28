package ru.sokomishalov.memeory.telegram.autoconfigure

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * @author sokomishalov
 */
@ConfigurationProperties(prefix = "memeory.telegram.bot")
class TelegramBotProperties {
    var username: String? = null
    var token: String? = null
}