package ru.sokomishalov.memeory.db.mongo.impl

import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import ru.sokomishalov.commons.core.reactor.await
import ru.sokomishalov.memeory.core.dto.TopicDTO
import ru.sokomishalov.memeory.db.TopicService
import ru.sokomishalov.memeory.db.mongo.mapper.TopicMapper
import ru.sokomishalov.memeory.db.mongo.repository.TopicRepository

@Service
@Primary
class MongoTopicService(
        private val topicRepository: TopicRepository,
        private val topicMapper: TopicMapper
) : TopicService {

    override suspend fun findAll(): List<TopicDTO> {
        return topicRepository
                .findAll()
                .await()
                .let { topicMapper.toDtoList(it) }
    }

    override suspend fun findById(id: String): TopicDTO? {
        return topicRepository
                .findById(id)
                .await()
                ?.let { topicMapper.toDto(it) }
    }

    override suspend fun save(vararg batch: TopicDTO): List<TopicDTO> {
        val topics = topicMapper.toEntityList(batch.toList())
        val savedTopics = topicRepository.saveAll(topics).await()
        return topicMapper.toDtoList(savedTopics)
    }
}