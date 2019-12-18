package ru.sokomishalov.memeory.db

import ru.sokomishalov.memeory.core.dto.ChannelDTO
import ru.sokomishalov.memeory.core.enums.Provider


interface ChannelService {

    suspend fun findAll(): List<ChannelDTO>

    suspend fun findById(id: String): ChannelDTO?

    suspend fun findByTopic(topicId: String): List<ChannelDTO>

    suspend fun findByProvider(providerId: Provider): List<ChannelDTO>

    suspend fun save(vararg batch: ChannelDTO): List<ChannelDTO>
}
