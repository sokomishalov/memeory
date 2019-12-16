package ru.sokomishalov.memeory.db

import ru.sokomishalov.memeory.core.dto.TopicDTO

interface TopicService {

    suspend fun findAll(): List<TopicDTO>

    suspend fun findById(id: String): TopicDTO?

    suspend fun save(vararg batch: TopicDTO): List<TopicDTO>
}