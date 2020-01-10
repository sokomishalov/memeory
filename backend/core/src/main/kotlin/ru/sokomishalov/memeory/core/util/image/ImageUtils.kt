package ru.sokomishalov.memeory.core.util.image

import org.springframework.web.reactive.function.client.awaitBody
import ru.sokomishalov.commons.core.reactor.awaitStrict
import ru.sokomishalov.commons.spring.webclient.REACTIVE_WEB_CLIENT
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import javax.imageio.ImageIO

suspend fun getImageByteArray(url: String?): ByteArray? {
    return runCatching {
        REACTIVE_WEB_CLIENT
                .get()
                .uri(url.orEmpty())
                .exchange()
                .awaitStrict()
                .awaitBody<ByteArray>()
    }.getOrNull()
}


fun ByteArray.toBufferedImage(): BufferedImage {
    return ByteArrayInputStream(this).use {
        ImageIO.read(it)
    }
}

suspend fun getImageDimensions(url: String?, default: Pair<Int, Int> = 1 to 1): Pair<Int, Int> {
    return runCatching {
        getImageByteArray(url)?.toBufferedImage()?.run { width to height }
    }.getOrNull() ?: default
}

suspend fun getImageAspectRatio(url: String?): Double {
    return getImageDimensions(url).run { first.toDouble() / second }
}