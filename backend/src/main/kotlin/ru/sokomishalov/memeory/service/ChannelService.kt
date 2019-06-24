package ru.sokomishalov.memeory.service

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.sokomishalov.memeory.dto.ChannelDTO

interface ChannelService {

    fun saveOne(channel: ChannelDTO): Mono<ChannelDTO>

    fun saveChannelsIfNotExist(vararg channels: ChannelDTO): Flux<ChannelDTO>

    fun findAllEnabled(): Flux<ChannelDTO>

    fun getLogoByChannelId(channelId: String): Mono<ByteArray>
}
