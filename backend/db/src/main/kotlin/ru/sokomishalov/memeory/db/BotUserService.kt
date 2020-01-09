package ru.sokomishalov.memeory.db

import ru.sokomishalov.memeory.core.dto.BotUserDTO


interface BotUserService {

    suspend fun findByUsername(username: String): BotUserDTO?

    suspend fun findAll(): List<BotUserDTO>

    suspend fun save(botUser: BotUserDTO): BotUserDTO

    suspend fun toggleTopic(username: String, topic: String): BotUserDTO?
}
