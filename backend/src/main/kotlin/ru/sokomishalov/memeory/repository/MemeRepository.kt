package ru.sokomishalov.memeory.repository

import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import ru.sokomishalov.memeory.entity.mongo.Meme

/**
 * @author sokomishalov
 */
@Repository
interface MemeRepository : ReactiveMongoRepository<Meme, String> {

    fun findMemeBy(page: Pageable): Flux<Meme>

}
