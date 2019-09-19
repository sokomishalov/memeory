package ru.sokomishalov.memeory.service.provider.facebook

import org.springframework.boot.context.properties.ConfigurationProperties
import ru.sokomishalov.memeory.service.provider.ProviderBaseConfigurationProperties

/**
 * @author sokomishalov
 */
@ConfigurationProperties(prefix = "provider.facebook")
class FacebookConfigurationProperties : ProviderBaseConfigurationProperties()
