package ru.sokomishalov.memeory.web

import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.service.ChannelService

/**
 * @author sokomishalov
 */
@RestController
@RequestMapping("/channels")
class ChannelController(private val channelServiceMongo: ChannelService) {

    @GetMapping("/list")
    fun all(): Flux<ChannelDTO> {
        return channelServiceMongo.findAll()
    }

    @PostMapping("/add")
    fun add(@RequestBody account: ChannelDTO): Mono<ChannelDTO> {
        return channelServiceMongo.saveOne(account)
    }
}
