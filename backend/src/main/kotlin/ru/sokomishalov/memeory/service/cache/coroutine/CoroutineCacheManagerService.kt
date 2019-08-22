@file:Suppress("UNCHECKED_CAST")

package ru.sokomishalov.memeory.service.cache.coroutine

import org.springframework.cache.CacheManager
import org.springframework.stereotype.Service
import ru.sokomishalov.memeory.condition.ConditionalOnUsingCoroutines


/**
 * @author sokomishalov
 */
@Service
@ConditionalOnUsingCoroutines
class CoroutineCacheManagerService(
        private val cacheManager: CacheManager
) : CoroutineCacheService {

    override suspend fun <T> getFromCache(cacheName: String, key: String, orElse: suspend () -> T): T {
        val cache = cacheManager.getCache(cacheName)
        val value = cache?.get(key)?.get()

        return when {
            value != null -> value as T
            else -> {
                val orElseValue = orElse()
                cache?.put(key, orElseValue)
                orElseValue
            }
        }
    }

    override suspend fun evictFromCache(cacheName: String, key: String) {
        cacheManager.getCache(cacheName)?.evict(key)
    }
}
