package ru.sokomishalov.memeory.service.provider.twitter

import org.springframework.boot.context.properties.ConfigurationProperties
import ru.sokomishalov.memeory.service.provider.ProviderBaseConfigurationProperties


/**
 * @author sokomishalov
 */
@ConfigurationProperties(prefix = "provider.twitter")
class TwitterConfigurationProperties : ProviderBaseConfigurationProperties()
