package ru.sokomishalov.memeory.service.api.reddit

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * @author sokomishalov
 */
@ConfigurationProperties(prefix = "channel.reddit")
class RedditConfigurationProperties {
    lateinit var baseUrl: String
}
