package ru.sokomishalov.memeory.service.cache

/**
 * @author sokomishalov
 */
interface CacheService {

    suspend fun <T> getFromCache(cacheName: String, key: String, orElse: suspend () -> T): T

    suspend fun evictFromCache(cacheName: String, key: String)

}