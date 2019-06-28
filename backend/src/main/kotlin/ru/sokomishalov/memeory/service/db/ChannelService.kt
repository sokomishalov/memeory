package ru.sokomishalov.memeory.service.db

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.sokomishalov.memeory.dto.ChannelDTO

interface ChannelService {

    fun findAllEnabled(): Flux<ChannelDTO>

    fun findAll(): Flux<ChannelDTO>

    fun findById(channelId: String): Mono<ChannelDTO>

    fun saveOne(channel: ChannelDTO): Mono<ChannelDTO>

    fun saveIfNotExist(vararg channels: ChannelDTO): Flux<ChannelDTO>

    fun toggleEnabled(enabled: Boolean, vararg channelIds: String): Mono<Void>
}
