package ru.sokomishalov.memeory.api.config

import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.sokomishalov.memeory.core.util.consts.CHANNEL_LOGO_CACHE_KEY


/**
 * @author sokomishalov
 */
@Configuration
@EnableCaching
class CacheConfig {

    @Bean
    fun cache(): CacheManager = ConcurrentMapCacheManager(CHANNEL_LOGO_CACHE_KEY)

}
