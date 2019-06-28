package ru.sokomishalov.memeory.service.provider.instagram

import org.springframework.boot.context.properties.ConfigurationProperties
import ru.sokomishalov.memeory.service.provider.ProviderBaseConfigurationProperties


/**
 * @author sokomishalov
 */
@ConfigurationProperties(prefix = "provider.instagram")
class InstagramConfigurationProperties : ProviderBaseConfigurationProperties()
