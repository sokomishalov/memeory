package ru.sokomishalov.memeory.db

import ru.sokomishalov.memeory.core.dto.ChannelDTO


interface ChannelService {

    suspend fun findAll(): List<ChannelDTO>

    suspend fun findById(id: String): ChannelDTO?

    suspend fun findByTopic(topicId: String): List<ChannelDTO>

    suspend fun save(vararg batch: ChannelDTO): List<ChannelDTO>
}
