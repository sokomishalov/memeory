package ru.sokomishalov.memeory.service.provider

import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.enums.SourceType

interface ProviderService {

    suspend fun fetchMemesFromChannel(channel: ChannelDTO): List<MemeDTO>

    suspend fun getLogoUrlByChannel(channel: ChannelDTO): String

    fun sourceType(): SourceType
}
