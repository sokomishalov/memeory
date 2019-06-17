package ru.sokomishalov.memeory.service.api.vk

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * @author sokomishalov
 */
@ConfigurationProperties(prefix = "channel.vk")
class VkConfigurationProperties {
    var appId: Int = 0
    lateinit var accessToken: String
}
