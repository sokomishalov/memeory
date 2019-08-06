package ru.sokomishalov.memeory.util.io

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.reactor.mono
import kotlinx.coroutines.withContext
import org.springframework.core.io.ByteArrayResource
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.util.function.Tuple2
import ru.sokomishalov.memeory.util.extensions.await
import java.net.URL
import reactor.util.function.Tuples.of as tupleOf
import javax.imageio.ImageIO.read as readImage


/**
 * @author sokomishalov
 */

suspend fun checkAttachmentAvailabilityAsync(url: String?) = withContext(IO) {
    try {
        readImage(URL(url))
        true
    } catch (t: Throwable) {
        false
    }
}

fun checkAttachmentAvailability(url: String): Boolean {
    return try {
        readImage(URL(url))
        true
    } catch (t: Throwable) {
        false
    }
}

fun getImageDimensions(url: String): Tuple2<Int, Int> {
    return try {
        readImage(URL(url)).let { tupleOf(it.width, it.height) }
    } catch (t: Throwable) {
        tupleOf(1, 1)
    }
}

fun getImageAspectRatio(url: String): Double {
    return getImageDimensions(url)
            .let { t: Tuple2<Int, Int> -> t.t1.toDouble().div(t.t2) }
}

fun getImageByteArrayMonoByUrl(url: String, webClient: WebClient = WebClient.create()): Mono<ByteArray> {
    return webClient
            .get()
            .uri(url)
            .exchange()
            .flatMap { it.bodyToMono(ByteArrayResource::class.java) }
            .map { it.byteArray }
}

suspend fun coGetImageByteArrayMonoByUrl(url: String, webClient: WebClient = WebClient.create()): Mono<ByteArray> = withContext(Unconfined) {
    mono {
        val response = webClient.get().uri(url).exchange().await()
        val resource = response?.bodyToMono(ByteArrayResource::class.java).await()
        resource?.byteArray
    }
}

