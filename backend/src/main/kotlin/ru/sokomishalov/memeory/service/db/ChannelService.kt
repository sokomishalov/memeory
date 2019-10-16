package ru.sokomishalov.memeory.service.db

import ru.sokomishalov.memeory.dto.ChannelDTO

interface ChannelService {

    suspend fun findAllEnabled(): List<ChannelDTO>

    suspend fun findAll(): List<ChannelDTO>

    suspend fun findById(id: String): ChannelDTO

    suspend fun save(channel: ChannelDTO): ChannelDTO

    suspend fun saveBatch(vararg batch: ChannelDTO): List<ChannelDTO>

    suspend fun toggleEnabled(enabled: Boolean, vararg channelIds: String)
}
