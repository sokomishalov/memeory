package ru.sokomishalov.memeory.web

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.io.AbstractResource
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpHeaders.CONTENT_DISPOSITION
import org.springframework.http.MediaType.APPLICATION_OCTET_STREAM
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import ru.sokomishalov.memeory.condition.ConditionalOnNotUsingCoroutines
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.service.cache.CacheService
import ru.sokomishalov.memeory.service.db.ChannelService
import ru.sokomishalov.memeory.service.provider.ProviderService
import ru.sokomishalov.memeory.util.consts.CHANNEL_LOGO_CACHE_KEY
import ru.sokomishalov.memeory.util.consts.ID_DELIMITER
import ru.sokomishalov.memeory.util.io.getImageByteArrayMonoByUrl
import org.springframework.http.ResponseEntity.ok as responseEntityOk
import reactor.core.publisher.Flux.fromIterable as fluxFromIterable

/**
 * @author sokomishalov
 */
@RestController
@RequestMapping("/channels")
@ConditionalOnNotUsingCoroutines
class ChannelController(private val channelService: ChannelService,
                        @Qualifier("placeholder")
                        private val placeholder: ByteArray,
                        private val providerServices: List<ProviderService>,
                        private val cache: CacheService,
                        private val webClient: WebClient
) {

    @GetMapping("/list")
    fun all(): Flux<ChannelDTO> {
        return channelService.findAll()
    }

    @GetMapping("/list/enabled")
    fun enabled(): Flux<ChannelDTO> {
        return channelService.findAllEnabled()
    }

    @PostMapping("/enable")
    fun enable(@RequestBody channelIds: List<String>): Mono<Unit> {
        return channelService.toggleEnabled(true, *channelIds.toTypedArray())
    }

    @PostMapping("/disable")
    fun disable(@RequestBody channelIds: List<String>): Mono<Unit> {
        return channelService.toggleEnabled(false, *channelIds.toTypedArray())
    }

    @PostMapping("/add")
    fun add(@RequestBody account: ChannelDTO): Mono<ChannelDTO> {
        return channelService.saveOne(account)
    }

    @GetMapping("/logo/{channelId}")
    fun logo(@PathVariable channelId: String): Mono<ResponseEntity<out AbstractResource>> {
        return cache
                .getFromCache(
                        cache = CHANNEL_LOGO_CACHE_KEY,
                        key = channelId,
                        orElse = channelService
                                .findById(channelId)
                                .flatMap { c ->
                                    fluxFromIterable(providerServices)
                                            .filter { it.sourceType() == c.sourceType }
                                            .next()
                                            .flatMap { it.getLogoUrlByChannel(c) }
                                            .flatMap { getImageByteArrayMonoByUrl(it, webClient) }
                                }
                )
                .defaultIfEmpty(placeholder)
                .onErrorResume { just(placeholder) }
                .map {
                    responseEntityOk()
                            .contentType(APPLICATION_OCTET_STREAM)
                            .contentLength(it.size.toLong())
                            .header(CONTENT_DISPOSITION, "attachment; filename=$channelId${ID_DELIMITER}logo.png")
                            .body(ByteArrayResource(it))
                }

    }
}
