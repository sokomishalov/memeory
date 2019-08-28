package ru.sokomishalov.memeory.web

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpHeaders.CONTENT_DISPOSITION
import org.springframework.http.MediaType.IMAGE_PNG
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.client.WebClient
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.service.cache.CacheService
import ru.sokomishalov.memeory.service.db.ChannelService
import ru.sokomishalov.memeory.service.provider.ProviderService
import ru.sokomishalov.memeory.util.consts.CHANNEL_LOGO_CACHE_KEY
import ru.sokomishalov.memeory.util.consts.ID_DELIMITER
import ru.sokomishalov.memeory.util.extensions.await
import ru.sokomishalov.memeory.util.extensions.awaitStrict
import ru.sokomishalov.memeory.util.extensions.unit
import ru.sokomishalov.memeory.util.io.aGetImageByteArrayMonoByUrl
import org.springframework.http.ResponseEntity.ok as responseEntityOk

/**
 * @author sokomishalov
 */
@RestController
@RequestMapping("/channels")
class ChannelController(private val channelService: ChannelService,
                        private val providerServices: List<ProviderService>,
                        private val cache: CacheService,
                        private val webClient: WebClient,
                        @Qualifier("placeholder")
                        private val placeholder: ByteArray
) {

    @GetMapping("/list")
    suspend fun all(): List<ChannelDTO> =
            channelService.findAll().await()

    @GetMapping("/list/enabled")
    suspend fun enabled(): List<ChannelDTO> =
            channelService.findAllEnabled().await()

    @PostMapping("/enable")
    suspend fun enable(@RequestBody channelIds: List<String>) =
            channelService.toggleEnabled(true, *channelIds.toTypedArray()).await().unit()

    @PostMapping("/disable")
    suspend fun disable(@RequestBody channelIds: List<String>) =
            channelService.toggleEnabled(false, *channelIds.toTypedArray()).await().unit()

    @PostMapping("/add")
    suspend fun add(@RequestBody account: ChannelDTO): ChannelDTO? =
            channelService.saveOne(account).await()

    @GetMapping("/logo/{channelId}")
    suspend fun logo(@PathVariable channelId: String): ResponseEntity<ByteArray> {
        val logoByteArray = cache.getFromCache(CHANNEL_LOGO_CACHE_KEY, channelId) {
            val channel = channelService.findById(channelId).awaitStrict()
            val service = providerServices.find { p -> p.sourceType() == channel.sourceType }

            runCatching {
                val url = service?.getLogoUrlByChannel(channel).await()
                aGetImageByteArrayMonoByUrl(url, webClient)
            }.getOrNull()
        } ?: placeholder

        return responseEntityOk()
                .contentType(IMAGE_PNG)
                .contentLength(logoByteArray.size.toLong())
                .header(CONTENT_DISPOSITION, "attachment; filename=$channelId${ID_DELIMITER}logo.png")
                .body(logoByteArray)
    }
}
