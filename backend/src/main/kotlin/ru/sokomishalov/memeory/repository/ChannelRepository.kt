package ru.sokomishalov.memeory.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import ru.sokomishalov.memeory.entity.mongo.Channel


/**
 * @author sokomishalov
 */
@Repository
interface ChannelRepository : ReactiveMongoRepository<Channel, String> {

    fun findAllByEnabled(enabled: Boolean): Flux<Channel>

}
