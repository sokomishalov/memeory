@file:Suppress("unused")

package ru.sokomishalov.memeory.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "memeory")
class MemeoryProperties {
    var fetchIntervalMs: Long = 60000
    var fetchCount: Int = 100
    var memeExpirationDays: Int = 30
    var enableCoroutines: Boolean = false
}
