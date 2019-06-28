package ru.sokomishalov.memeory.service.provider.reddit

import org.springframework.boot.context.properties.ConfigurationProperties
import ru.sokomishalov.memeory.service.provider.ProviderBaseConfigurationProperties

/**
 * @author sokomishalov
 */
@ConfigurationProperties(prefix = "provider.reddit")
class RedditConfigurationProperties : ProviderBaseConfigurationProperties()
