package ru.sokomishalov.memeory.api.web

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.CacheManager
import org.springframework.http.HttpHeaders.ACCESS_CONTROL_MAX_AGE
import org.springframework.http.HttpHeaders.CONTENT_DISPOSITION
import org.springframework.http.MediaType.IMAGE_PNG
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sokomishalov.commons.spring.cache.get
import ru.sokomishalov.commons.spring.cache.put
import ru.sokomishalov.memeory.core.dto.ChannelDTO
import ru.sokomishalov.memeory.core.util.consts.CHANNEL_LOGO_CACHE_KEY
import ru.sokomishalov.memeory.core.util.consts.DELIMITER
import ru.sokomishalov.memeory.core.util.image.getImageByteArray
import ru.sokomishalov.memeory.db.ChannelService
import ru.sokomishalov.memeory.providers.ProviderFactory
import org.springframework.http.ResponseEntity.ok as responseEntityOk

/**
 * @author sokomishalov
 */
@RestController
@RequestMapping("/channels")
class ChannelController(private val channelService: ChannelService,
                        private val providerFactory: ProviderFactory,
                        private val cache: CacheManager,
                        @Qualifier("placeholder")
                        private val placeholder: ByteArray
) {

    @GetMapping("/list")
    suspend fun list(): List<ChannelDTO> {
        return channelService.findAll()
    }

    @GetMapping("/logo/{channelId}")
    suspend fun logo(@PathVariable("channelId") channelId: String): ResponseEntity<ByteArray> {
        val logoByteArray = cache.get<ByteArray>(CHANNEL_LOGO_CACHE_KEY, channelId) ?: run {
            val url = runCatching {
                val channel = channelService.findById(channelId)
                channel?.let { providerFactory[it.provider]?.getLogoUrl(it) }
            }.getOrNull()

            getImageByteArray(url, orElse = placeholder).also {
                cache.put(CHANNEL_LOGO_CACHE_KEY, channelId, it)
            }
        }

        return responseEntityOk()
                .contentType(IMAGE_PNG)
                .contentLength(logoByteArray.size.toLong())
                .header(CONTENT_DISPOSITION, "attachment; filename=$channelId${DELIMITER}logo.png")
                .header(ACCESS_CONTROL_MAX_AGE, "31536000", "public")
                .body(logoByteArray)
    }
}
