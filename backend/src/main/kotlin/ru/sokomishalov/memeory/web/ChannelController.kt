package ru.sokomishalov.memeory.web

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.io.AbstractResource
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.MediaType.APPLICATION_OCTET_STREAM
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.service.cache.CacheService
import ru.sokomishalov.memeory.service.db.ChannelService
import ru.sokomishalov.memeory.service.provider.ProviderService
import ru.sokomishalov.memeory.util.CHANNEL_LOGO_CACHE_KEY

/**
 * @author sokomishalov
 */
@RestController
@RequestMapping("/channels")
class ChannelController(private val channelService: ChannelService,
                        @Qualifier("placeholder")
                        private val placeholder: ByteArray,
                        private val providerServices: List<ProviderService>,
                        private val cache: CacheService) {

    @GetMapping("/list")
    fun all(): Flux<ChannelDTO> {
        return channelService.findAllEnabled()
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
                                    Flux.fromIterable(providerServices)
                                            .filter { it.sourceType() == c.sourceType }
                                            .next()
                                            .flatMap { it.getLogoByChannel(c) }
                                }
                )
                .defaultIfEmpty(placeholder)
                .onErrorResume { just(placeholder) }
                .map {
                    ResponseEntity
                            .ok()
                            .contentType(APPLICATION_OCTET_STREAM)
                            .body(ByteArrayResource(it))
                }

    }
}
