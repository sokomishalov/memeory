package ru.sokomishalov.memeory.service.cache

import org.springframework.cache.CacheManager
import org.springframework.stereotype.Service
import reactor.cache.CacheMono.lookup
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.fromRunnable
import reactor.core.publisher.Mono.justOrEmpty
import reactor.core.publisher.Signal
import ru.sokomishalov.memeory.condition.ConditionalOnNotUsingCoroutines


/**
 * @author sokomishalov
 */
@Service
@ConditionalOnNotUsingCoroutines
class CacheService(private val cacheManager: CacheManager) {

    @Suppress("UNCHECKED_CAST")
    fun <T> getFromCache(cache: String, key: String, orElse: Mono<T>): Mono<T> {
        return lookup({ justOrEmpty(cacheManager.getCache(cache)?.get(it)?.get() as Signal<*>?) }, key)
                .onCacheMissResume { orElse.map { it } }
                .andWriteWith { k: String, v: Signal<*> -> fromRunnable { cacheManager.getCache(cache)?.put(k, v) } }
                .map { it as T }
                .onErrorResume { orElse }
    }
}
