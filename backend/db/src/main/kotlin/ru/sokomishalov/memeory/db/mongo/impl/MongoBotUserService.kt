package ru.sokomishalov.memeory.db.mongo.impl

import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import ru.sokomishalov.commons.core.reactor.await
import ru.sokomishalov.commons.core.reactor.awaitStrict
import ru.sokomishalov.memeory.core.dto.BotUserDTO
import ru.sokomishalov.memeory.db.BotUserService
import ru.sokomishalov.memeory.db.mongo.mapper.BotUserMapper
import ru.sokomishalov.memeory.db.mongo.repository.BotUserRepository

@Service
@Primary
class MongoBotUserService(
        private val botUserRepository: BotUserRepository,
        private val botUserMapper: BotUserMapper
) : BotUserService {

    override suspend fun findByUsername(username: String): BotUserDTO? {
        val entity = botUserRepository.findById(username).await()
        return entity?.let { botUserMapper.toDto(it) }
    }

    override suspend fun findAll(): List<BotUserDTO> {
        val entities = botUserRepository.findAll().await()
        return botUserMapper.toDtoList(entities)
    }

    override suspend fun save(botUser: BotUserDTO): BotUserDTO {
        val entity = botUserMapper.toEntity(botUser)
        val existedUser = botUserRepository.findById(botUser.username).await()

        return when {
            existedUser != null -> botUserMapper.toDto(existedUser)
            else -> {
                val savedEntity = botUserRepository.save(entity).awaitStrict()
                botUserMapper.toDto(savedEntity)
            }
        }
    }

    override suspend fun toggleTopic(username: String, topic: String): BotUserDTO? {
        val botUser = botUserRepository.findById(username).await()

        val topics = botUser?.topics.orEmpty().toMutableList()
        when (topic) {
            in topics -> topics -= topic
            else -> topics += topic
        }

        val updatedUser = botUser?.copy(topics = topics)?.let { botUserRepository.save(it).await() }
        return updatedUser?.let { botUserMapper.toDto(it) }
    }
}