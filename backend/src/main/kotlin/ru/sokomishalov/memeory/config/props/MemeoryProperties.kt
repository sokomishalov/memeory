package ru.sokomishalov.memeory.config.props

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "memeory")
class MemeoryProperties {
    var fetchIntervalMs: Long = 60000
    val fetchCount: Int = 100
    val memeExpirationDays: Int = 30
}
