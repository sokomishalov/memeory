package ru.sokomishalov.memeory.util.extensions

import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import org.reactivestreams.Publisher
import org.springframework.web.reactive.function.BodyInserters.fromPublisher
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import org.springframework.web.reactive.function.server.ServerResponse.ok as serverResponseOk


/**
 * @author sokomishalov
 */

suspend inline fun <reified T> Publisher<T>.awaitResponse(): ServerResponse = serverResponseOk().body(fromPublisher(this, T::class.java)).awaitStrict()

suspend inline fun <T> Flux<T>?.await(): List<T> = this?.collectList()?.awaitFirstOrNull() ?: emptyList()

suspend inline fun <T> Mono<T>?.await(): T? = this?.awaitFirstOrNull()

suspend fun <T> Publisher<T>.awaitOrElse(defaultValue: () -> T): T = awaitFirstOrNull() ?: defaultValue()

suspend inline fun <T> Mono<T>?.awaitStrict(): T = this?.awaitFirstOrNull() ?: Mono.empty<T>().awaitSingle()
