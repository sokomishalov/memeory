package ru.sokomishalov.memeory.util

import org.springframework.core.io.ByteArrayResource
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.util.function.Tuple2
import java.net.URL
import reactor.util.function.Tuples.of as tupleOf
import javax.imageio.ImageIO.read as readImage


/**
 * @author sokomishalov
 */

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
