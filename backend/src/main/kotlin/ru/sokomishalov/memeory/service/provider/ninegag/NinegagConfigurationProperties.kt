package ru.sokomishalov.memeory.service.provider.ninegag

import org.springframework.boot.context.properties.ConfigurationProperties
import ru.sokomishalov.memeory.service.provider.ProviderBaseConfigurationProperties


/**
 * @author sokomishalov
 */
@ConfigurationProperties(prefix = "provider.ninegag")
class NinegagConfigurationProperties : ProviderBaseConfigurationProperties()
