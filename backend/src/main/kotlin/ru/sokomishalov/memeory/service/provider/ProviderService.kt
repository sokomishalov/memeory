package ru.sokomishalov.memeory.service.provider

import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.enums.Provider

interface ProviderService {

    val provider: Provider

    suspend fun fetchMemes(channel: ChannelDTO): List<MemeDTO>

    suspend fun getLogoUrl(channel: ChannelDTO): String?
}
