package ru.sokomishalov.memeory.db

import ru.sokomishalov.memeory.core.dto.BotUserDTO


interface BotUserService {

    suspend fun findById(id: String): BotUserDTO?

    suspend fun findAll(): List<BotUserDTO>

    suspend fun save(botUser: BotUserDTO): BotUserDTO
}
