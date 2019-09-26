package ru.sokomishalov.memeory.service.db.mongo.repository

import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import ru.sokomishalov.memeory.service.db.mongo.entity.Meme

/**
 * @author sokomishalov
 */
@Repository
interface MemeRepository : ReactiveMongoRepository<Meme, String> {

    fun findAllMemesBy(page: Pageable): Flux<Meme>

    fun findAllByChannelIdIn(channelIds: Iterable<String>, page: Pageable): Flux<Meme>
}
