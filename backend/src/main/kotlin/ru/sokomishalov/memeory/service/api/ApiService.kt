package ru.sokomishalov.memeory.service.api

import reactor.core.publisher.Flux
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.enums.SourceType

interface ApiService {

    fun fetchMemesFromChannels(vararg channels: ChannelDTO): Flux<MemeDTO>

    fun sourceType(): SourceType

}