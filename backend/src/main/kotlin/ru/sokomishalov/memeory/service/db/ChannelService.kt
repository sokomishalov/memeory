package ru.sokomishalov.memeory.service.db

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.sokomishalov.memeory.dto.ChannelDTO

interface ChannelService {

    fun findAllEnabled(): Flux<ChannelDTO>

    fun findById(channelId: String): Mono<ChannelDTO>

    fun saveOne(channel: ChannelDTO): Mono<ChannelDTO>

    fun saveChannelsIfNotExist(vararg channels: ChannelDTO): Flux<ChannelDTO>
}
