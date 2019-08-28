@file:Suppress("RemoveExplicitTypeArguments", "unused")

package ru.sokomishalov.memeory.util.extensions

import org.reactivestreams.Publisher
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.BodyInserters.fromPublisher
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.ServerResponse.ok as serverResponseOk

/**
 * @author sokomishalov
 */

suspend inline fun <reified T : Any> ServerRequest.awaitRequest(): T =
        awaitBody<T>()

suspend inline fun <reified T> Publisher<T>.awaitResponse(): ServerResponse =
        serverResponseOk()
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .body(fromPublisher(this, T::class.java))
                .awaitStrict()

suspend inline fun <reified T> T.awaitResponse(): ServerResponse =
        serverResponseOk()
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .body(fromObject(this))
                .awaitStrict()