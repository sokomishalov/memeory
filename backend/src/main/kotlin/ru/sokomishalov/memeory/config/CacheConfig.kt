package ru.sokomishalov.memeory.config

import com.github.benmanes.caffeine.cache.Caffeine.newBuilder
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCache
import org.springframework.cache.support.SimpleCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import ru.sokomishalov.memeory.util.CHANNEL_LOGO_CACHE_KEY
import java.time.Duration.ofDays


/**
 * @author sokomishalov
 */
@Configuration
@EnableCaching
class CacheConfig {

    @Bean
    @Primary
    fun cache(): CacheManager {
        return SimpleCacheManager().apply {
            setCaches(listOf(
                    buildSimpleCaffeineCache(CHANNEL_LOGO_CACHE_KEY)
            ))
        }
    }

    private fun buildSimpleCaffeineCache(key: String): CaffeineCache {
        return CaffeineCache(key, newBuilder().expireAfterAccess(ofDays(1)).build())
    }
}
