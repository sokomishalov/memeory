package ru.sokomishalov.memeory.core.util.image

import org.springframework.web.reactive.function.client.awaitBody
import ru.sokomishalov.commons.core.reactor.awaitStrict
import ru.sokomishalov.commons.spring.webclient.REACTIVE_WEB_CLIENT

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