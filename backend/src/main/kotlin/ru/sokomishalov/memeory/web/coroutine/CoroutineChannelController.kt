package ru.sokomishalov.memeory.web.coroutine

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.reactor.mono
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpHeaders.CONTENT_DISPOSITION
import org.springframework.http.MediaType.APPLICATION_OCTET_STREAM
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.coRouter
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.service.cache.CacheService
import ru.sokomishalov.memeory.service.db.ChannelService
import ru.sokomishalov.memeory.service.provider.ProviderService
import ru.sokomishalov.memeory.util.CHANNEL_LOGO_CACHE_KEY
import ru.sokomishalov.memeory.util.COROUTINE_PATH_PREFIX
import ru.sokomishalov.memeory.util.ID_DELIMITER
import ru.sokomishalov.memeory.util.extensions.awaitOrElse
import ru.sokomishalov.memeory.util.extensions.awaitResponse
import ru.sokomishalov.memeory.util.extensions.awaitStrict
import org.springframework.web.reactive.function.server.ServerResponse.ok as serverResponseOk


/**
 * @author sokomishalov
 */
@Configuration
class CoroutineChannelController(
        private val channelService: ChannelService,
        private val providerServices: List<ProviderService>,
        private val cache: CacheService,
        @Qualifier("placeholder")
        private val placeholder: ByteArray
) {

    @Bean
    fun channelCoroutineRouter() = coRouter {
        "$COROUTINE_PATH_PREFIX/channels".nest {

            GET("/list") {
                channelService.findAll().awaitResponse()
            }

            GET("/list/enabled") {
                channelService.findAllEnabled().awaitResponse()
            }

            POST("/enable") {
                val channelIds = it.awaitBody<Array<String>>()
                channelService.toggleEnabled(true, *channelIds).awaitResponse()
            }

            POST("/disable") {
                val channelIds = it.awaitBody<Array<String>>()
                channelService.toggleEnabled(false, *channelIds).awaitResponse()
            }

            POST("/add") {
                val channel = it.awaitBody<ChannelDTO>()
                channelService.saveOne(channel).awaitResponse()
            }

            GET("/logo/{channelId}") {
                val channelId = it.pathVariable("channelId")

                val logoByteArray = cache.getFromCache(
                        cache = CHANNEL_LOGO_CACHE_KEY,
                        key = channelId,
                        orElse = GlobalScope.mono {
                            val channel = channelService.findById(channelId).awaitStrict()
                            val service = providerServices.find { p -> p.sourceType() == channel.sourceType }

                            service?.getLogoByChannel(channel)?.awaitOrElse { placeholder }
                        }
                ).awaitStrict()

                serverResponseOk()
                        .contentType(APPLICATION_OCTET_STREAM)
                        .contentLength(logoByteArray.size.toLong())
                        .header(CONTENT_DISPOSITION, "attachment; filename=$channelId${ID_DELIMITER}logo.png")
                        .bodyAndAwait(ByteArrayResource(logoByteArray))
            }
        }
    }
}
