package ru.sokomishalov.memeory.service.provider.vk

import org.springframework.boot.context.properties.ConfigurationProperties
import ru.sokomishalov.memeory.service.provider.ProviderBaseConfigurationProperties

/**
 * @author sokomishalov
 */
@ConfigurationProperties(prefix = "provider.vk")
class VkConfigurationProperties : ProviderBaseConfigurationProperties() {
    var appId: Int = 0
    lateinit var accessToken: String
}
