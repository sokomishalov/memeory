package ru.sokomishalov.memeory.web.coroutine

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpHeaders.CONTENT_DISPOSITION
import org.springframework.http.MediaType.APPLICATION_OCTET_STREAM
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyAndAwait
import ru.sokomishalov.memeory.condition.ConditionalOnUsingCoroutines
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.service.cache.coroutine.CoroutineCacheService
import ru.sokomishalov.memeory.service.db.ChannelService
import ru.sokomishalov.memeory.service.provider.ProviderService
import ru.sokomishalov.memeory.util.CHANNEL_LOGO_CACHE_KEY
import ru.sokomishalov.memeory.util.ID_DELIMITER
import ru.sokomishalov.memeory.util.extensions.awaitOrElse
import ru.sokomishalov.memeory.util.extensions.awaitResponse
import ru.sokomishalov.memeory.util.extensions.awaitStrict
import org.springframework.web.reactive.function.server.ServerResponse.ok as serverResponseOk


/**
 * @author sokomishalov
 */
@Component
@ConditionalOnUsingCoroutines
class CoroutineChannelHandler(
        private val channelService: ChannelService,
        private val providerServices: List<ProviderService>,
        private val cache: CoroutineCacheService,
        @Qualifier("placeholder")
        private val placeholder: ByteArray
) {
    suspend fun findAll(request: ServerRequest): ServerResponse {
        return channelService.findAll().awaitResponse()
    }

    suspend fun findAllEnabled(request: ServerRequest): ServerResponse {
        return channelService.findAllEnabled().awaitResponse()
    }

    suspend fun enableChannel(request: ServerRequest): ServerResponse {
        val channelIds = request.awaitBody<Array<String>>()
        return channelService.toggleEnabled(true, *channelIds).awaitResponse()
    }

    suspend fun disableChannel(request: ServerRequest): ServerResponse {
        val channelIds = request.awaitBody<Array<String>>()
        return channelService.toggleEnabled(false, *channelIds).awaitResponse()
    }

    suspend fun addChannel(request: ServerRequest): ServerResponse {
        val channel = request.awaitBody<ChannelDTO>()
        return channelService.saveOne(channel).awaitResponse()
    }

    suspend fun getChannelLogo(request: ServerRequest): ServerResponse {
        val channelId = request.pathVariable("channelId")

        val logoByteArray = cache.getFromCache(
                cacheName = CHANNEL_LOGO_CACHE_KEY,
                key = channelId,
                orElse = {
                    val channel = channelService.findById(channelId).awaitStrict()
                    val service = providerServices.find { p -> p.sourceType() == channel.sourceType }

                    service?.getLogoByChannel(channel)?.awaitOrElse { placeholder }
                }
        ) ?: placeholder

        return serverResponseOk()
                .contentType(APPLICATION_OCTET_STREAM)
                .contentLength(logoByteArray.size.toLong())
                .header(CONTENT_DISPOSITION, "attachment; filename=$channelId${ID_DELIMITER}logo.png")
                .bodyAndAwait(ByteArrayResource(logoByteArray))
    }
}
