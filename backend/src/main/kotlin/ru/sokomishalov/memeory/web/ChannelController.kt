package ru.sokomishalov.memeory.web

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpHeaders.CONTENT_DISPOSITION
import org.springframework.http.MediaType.IMAGE_PNG
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.client.WebClient
import ru.sokomishalov.commons.spring.cache.CacheService
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.service.db.ChannelService
import ru.sokomishalov.memeory.service.provider.ProviderService
import ru.sokomishalov.memeory.util.consts.CHANNEL_LOGO_CACHE_KEY
import ru.sokomishalov.memeory.util.consts.ID_DELIMITER
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
            channelService.findAll()

    @GetMapping("/list/enabled")
    suspend fun enabled(): List<ChannelDTO> =
            channelService.findAllEnabled()

    @PostMapping("/enable")
    suspend fun enable(@RequestBody channelIds: List<String>) =
            channelService.toggleEnabled(true, *channelIds.toTypedArray())

    @PostMapping("/disable")
    suspend fun disable(@RequestBody channelIds: List<String>) =
            channelService.toggleEnabled(false, *channelIds.toTypedArray())

    @PostMapping("/add")
    suspend fun add(@RequestBody account: ChannelDTO): ChannelDTO? =
            channelService.saveOne(account)

    @GetMapping("/logo/{channelId}")
    suspend fun logo(@PathVariable channelId: String): ResponseEntity<ByteArray> {
        val logoByteArray = cache.get(CHANNEL_LOGO_CACHE_KEY, channelId) {
            val channel = channelService.findById(channelId)
            val service = providerServices.find { p -> p.sourceType() == channel.sourceType }

            runCatching {
                val url = service?.getLogoUrlByChannel(channel)
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
