@file:Suppress("unused")

package ru.sokomishalov.memeory.api.autoconfigure

import org.springframework.boot.context.properties.ConfigurationProperties
import ru.sokomishalov.memeory.core.dto.AdminUserDTO
import java.time.Duration
import java.time.Duration.ofDays
import java.time.Duration.ofMinutes

@ConfigurationProperties(prefix = "memeory")
class MemeoryProperties {
    var fetchLimit: Int = 100
    var fetchInterval: Duration = ofMinutes(30)
    var memeLifeTime: Duration = ofDays(10)
    var useClusterLocks: Boolean = false
    var admins: List<AdminUserDTO> = emptyList()
}
