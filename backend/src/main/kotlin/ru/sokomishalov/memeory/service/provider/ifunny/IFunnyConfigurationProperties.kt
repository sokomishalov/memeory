package ru.sokomishalov.memeory.service.provider.ifunny

import org.springframework.boot.context.properties.ConfigurationProperties
import ru.sokomishalov.memeory.service.provider.ProviderBaseConfigurationProperties


/**
 * @author sokomishalov
 */
@ConfigurationProperties(prefix = "provider.ifunny")
class IFunnyConfigurationProperties : ProviderBaseConfigurationProperties()
