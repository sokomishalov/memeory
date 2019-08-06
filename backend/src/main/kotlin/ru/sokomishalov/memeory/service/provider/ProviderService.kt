package ru.sokomishalov.memeory.service.provider

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.enums.SourceType

interface ProviderService {

    fun fetchMemesFromChannel(channel: ChannelDTO): Flux<MemeDTO>

    fun getLogoUrlByChannel(channel: ChannelDTO): Mono<String>

    fun sourceType(): SourceType
}
