package ru.sokomishalov.memeory.service.cache.coroutine

/**
 * @author sokomishalov
 */
interface CoroutineCacheService {

    suspend fun <T> getFromCache(cacheName: String, key: String, orElse: suspend () -> T): T

    suspend fun evictFromCache(cacheName: String, key: String)

}