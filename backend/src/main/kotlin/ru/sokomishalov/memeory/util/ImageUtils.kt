package ru.sokomishalov.memeory.util

import org.springframework.core.io.ByteArrayResource
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.util.function.Tuple2
import reactor.util.function.Tuples.of
import java.net.URL
import javax.imageio.ImageIO.read


/**
 * @author sokomishalov
 */

fun getImageAspectRatio(url: String): Double {
    return getImageDimensions(url)
            .let { t: Tuple2<Int, Int> -> t.t1.toDouble().div(t.t2) }
}

fun getImageDimensions(url: String): Tuple2<Int, Int> {
    return try {
        URL(url)
                .let { read(it) }
                .let { of(it.width, it.height) }
    } catch (t: Throwable) {
        of(1, 1)
    }
}

fun getImageByteArrayMonoByUrl(url: String, webClient: WebClient = WebClient.create()): Mono<ByteArray> {
    return webClient
            .get()
            .uri(url)
            .exchange()
            .flatMap { it.bodyToMono(ByteArrayResource::class.java) }
            .map { it.byteArray }
}
