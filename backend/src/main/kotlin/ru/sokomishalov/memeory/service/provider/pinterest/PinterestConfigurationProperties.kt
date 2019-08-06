package ru.sokomishalov.memeory.service.provider.pinterest

import org.springframework.boot.context.properties.ConfigurationProperties
import ru.sokomishalov.memeory.service.provider.ProviderBaseConfigurationProperties


/**
 * @author sokomishalov
 */
@ConfigurationProperties(prefix = "provider.pinterest")
class PinterestConfigurationProperties : ProviderBaseConfigurationProperties()
