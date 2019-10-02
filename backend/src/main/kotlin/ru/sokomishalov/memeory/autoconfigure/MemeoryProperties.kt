@file:Suppress("unused")

package ru.sokomishalov.memeory.autoconfigure

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration
import java.time.Duration.ofDays
import java.time.Duration.ofMinutes

@ConfigurationProperties(prefix = "memeory")
class MemeoryProperties {
    var fetchMaxCount: Int = 100
    var fetchInterval: Duration = ofMinutes(5)
    var memeLifeTime: Duration = ofDays(10)
    var checkUrls: Boolean = false
    var admins: List<AdminUser> = emptyList()
}
