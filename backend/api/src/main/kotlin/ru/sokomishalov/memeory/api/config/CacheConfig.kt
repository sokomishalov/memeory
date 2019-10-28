package ru.sokomishalov.memeory.api.config

import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.sokomishalov.commons.spring.cache.CacheManagerService
import ru.sokomishalov.commons.spring.cache.CacheService
import ru.sokomishalov.memeory.core.util.consts.CHANNEL_LOGO_CACHE_KEY


/**
 * @author sokomishalov
 */
@Configuration
@EnableCaching
class CacheConfig {

    @Bean
    fun cache(): CacheService = CacheManagerService(caches = listOf(CHANNEL_LOGO_CACHE_KEY))

}
