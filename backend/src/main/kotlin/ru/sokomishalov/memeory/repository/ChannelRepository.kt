package ru.sokomishalov.memeory.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import ru.sokomishalov.memeory.entity.Channel


/**
 * @author sokomishalov
 */
@Repository
interface ChannelRepository : ReactiveMongoRepository<Channel, String>
