package ru.sokomishalov.memeory.service.api.facebook

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * @author sokomishalov
 */
@ConfigurationProperties(prefix = "channel.facebook")
class FacebookConfigurationProperties {
    var enabled: Boolean = false
    var scrapeEnabled: Boolean = false
    lateinit var appId: String
    lateinit var secret: String
}
