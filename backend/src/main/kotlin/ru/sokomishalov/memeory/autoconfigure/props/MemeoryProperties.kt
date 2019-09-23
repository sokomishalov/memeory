@file:Suppress("unused")

package ru.sokomishalov.memeory.autoconfigure.props

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "memeory")
class MemeoryProperties {
    var fetchIntervalMs: Long = 60000
    var fetchCount: Int = 100
    var memeExpirationDays: Int = 30
}
