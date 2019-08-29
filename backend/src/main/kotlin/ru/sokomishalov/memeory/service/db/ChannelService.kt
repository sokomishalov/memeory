package ru.sokomishalov.memeory.service.db

import ru.sokomishalov.memeory.dto.ChannelDTO

interface ChannelService {

    suspend fun findAllEnabled(): List<ChannelDTO>

    suspend fun findAll(): List<ChannelDTO>

    suspend fun findById(channelId: String): ChannelDTO

    suspend fun saveOne(channel: ChannelDTO): ChannelDTO

    suspend fun saveIfNotExist(vararg channels: ChannelDTO): List<ChannelDTO>

    suspend fun toggleEnabled(enabled: Boolean, vararg channelIds: String)
}
