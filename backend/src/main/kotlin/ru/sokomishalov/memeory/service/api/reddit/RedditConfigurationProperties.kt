package ru.sokomishalov.memeory.service.api.reddit

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * @author sokomishalov
 */
@ConfigurationProperties(prefix = "channel.reddit")
class RedditConfigurationProperties {
    var enabled: Boolean = false
    lateinit var baseUrl: String
}
