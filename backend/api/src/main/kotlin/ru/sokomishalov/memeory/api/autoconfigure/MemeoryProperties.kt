@file:Suppress("unused")

package ru.sokomishalov.memeory.api.autoconfigure

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import ru.sokomishalov.memeory.core.dto.AdminUserDTO
import java.time.Duration
import java.time.Duration.ofDays
import java.time.Duration.ofMinutes

@ConfigurationProperties(prefix = "memeory")
@ConstructorBinding
class MemeoryProperties(
        val fetchLimit: Int = 100,
        val fetchInterval: Duration = ofMinutes(30),
        val memeLifeTime: Duration = ofDays(10),
        val useClusterLocks: Boolean = false,
        val admins: List<AdminUserDTO> = emptyList()
)
