package ru.sokomishalov.memeory.util.extensions

import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext


/**
 * @author sokomishalov
 */

suspend inline fun <T> Iterable<T>.coForEach(noinline block: suspend (T) -> Unit) = withContext(coroutineContext) {
    map { async { block(it) } }.forEach { it.await() }
}

suspend inline fun <T, R> Iterable<T>.coMap(noinline transform: suspend (T) -> R): List<R> = withContext(coroutineContext) {
    map { async { transform(it) } }.map { it.await() }
}

suspend inline fun <T> Iterable<T>.coFilter(crossinline predicate: suspend (T) -> Boolean): List<T> = withContext(coroutineContext) {
    val destination = ArrayList<T>()
    map { async { if (predicate(it)) destination.add(it) } }.forEach { it.await() }
    destination
}
