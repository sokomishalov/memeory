package ru.sokomishalov.memeory.db.mongo.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import ru.sokomishalov.memeory.db.mongo.entity.Topic

@Repository
interface TopicRepository : ReactiveMongoRepository<Topic, String>