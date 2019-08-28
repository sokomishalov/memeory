@file:Suppress("unused")

package ru.sokomishalov.memeory.util.io

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import org.springframework.core.io.ByteArrayResource
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClient.create
import reactor.core.publisher.Mono
import reactor.util.function.Tuple2
import ru.sokomishalov.memeory.util.consts.EMPTY
import ru.sokomishalov.memeory.util.extensions.await
import ru.sokomishalov.memeory.util.restclient.REACTIVE_WEB_CLIENT
import java.net.URL
import reactor.util.function.Tuples.of as tupleOf
import javax.imageio.ImageIO.read as readImage


/**
 * @author sokomishalov
 */

fun checkAttachmentAvailability(url: String?): Boolean = runCatching {
    readImage(URL(url))
    true
}.getOrElse {
    false
}

fun getImageDimensions(url: String?): Tuple2<Int, Int> = runCatching {
    readImage(URL(url)).let { tupleOf(it.width, it.height) }
}.getOrElse {
    tupleOf(1, 1)
}

fun getImageAspectRatio(url: String?): Double {
    return getImageDimensions(url)
            .let { it.t1.toDouble().div(it.t2) }
}

fun getImageByteArrayMonoByUrl(url: String, webClient: WebClient = REACTIVE_WEB_CLIENT): Mono<ByteArray> {
    return webClient
            .get()
            .uri(url)
            .exchange()
            .flatMap { it.bodyToMono(ByteArrayResource::class.java) }
            .map { it.byteArray }
}


suspend fun aGetImageDimensions(url: String?): Tuple2<Int, Int> = withContext(IO) {
    getImageDimensions(url)
}

suspend fun aGetImageAspectRatio(url: String?): Double = withContext(IO) {
    getImageAspectRatio(url)
}

suspend fun aCheckAttachmentAvailability(url: String?) = withContext(IO) {
    checkAttachmentAvailability(url)
}

suspend fun aGetImageByteArrayMonoByUrl(url: String?, webClient: WebClient = create()): ByteArray? {
    val response = webClient.get().uri(url ?: EMPTY).exchange().await()
    val resource = response?.bodyToMono(ByteArrayResource::class.java).await()
    return resource?.byteArray
}
