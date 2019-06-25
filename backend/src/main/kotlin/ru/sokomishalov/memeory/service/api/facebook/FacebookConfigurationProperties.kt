package ru.sokomishalov.memeory.service.api.facebook

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * @author sokomishalov
 */
@ConfigurationProperties(prefix = "channel.facebook")
class FacebookConfigurationProperties {
    lateinit var appId: String
    lateinit var secret: String
}
