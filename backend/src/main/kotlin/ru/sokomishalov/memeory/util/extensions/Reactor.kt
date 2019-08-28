@file:Suppress("unused")

package ru.sokomishalov.memeory.util.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactor.flux
import kotlinx.coroutines.reactor.mono
import org.reactivestreams.Publisher
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.reactive.function.BodyInserters.fromPublisher
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import org.springframework.web.reactive.function.server.ServerResponse.ok as serverResponseOk
import reactor.core.publisher.Mono.empty as monoEmpty


/**
 * @author sokomishalov
 */

// remove when it will be possible to switch on 1.3.0 coroutines version
fun <T> mono(
        context: CoroutineContext = EmptyCoroutineContext,
        block: suspend CoroutineScope.() -> T?
): Mono<T> = GlobalScope.mono(context, block)

@ExperimentalCoroutinesApi
fun <T> flux(
        context: CoroutineContext = EmptyCoroutineContext,
        block: suspend ProducerScope<T>.() -> Unit
): Flux<T> = GlobalScope.flux(context, block)


suspend inline fun <reified T> Publisher<T>.awaitResponse(): ServerResponse =
        serverResponseOk()
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .body(fromPublisher(this, T::class.java))
                .awaitStrict()

suspend inline fun <T> Flux<T>?.await(): List<T> = this?.collectList().await() ?: emptyList()

suspend inline fun <T> Mono<T>?.await(): T? = this?.awaitFirstOrNull()

suspend inline fun <T> Flux<T>?.awaitUnit(): Unit = this.await().unit()

suspend inline fun <T> Mono<T>?.awaitUnit(): Unit = this.await().unit()

suspend fun <T> Publisher<T>.awaitOrElse(defaultValue: () -> T): T = awaitFirstOrNull() ?: defaultValue()

suspend inline fun <T> Mono<T>?.awaitStrict(): T = this?.awaitFirstOrNull() ?: monoEmpty<T>().awaitSingle()
