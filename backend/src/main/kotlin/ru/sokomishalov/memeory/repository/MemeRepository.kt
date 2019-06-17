package ru.sokomishalov.memeory.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import ru.sokomishalov.memeory.entity.Meme

/**
 * @author sokomishalov
 */
@Repository
interface MemeRepository : ReactiveMongoRepository<Meme, String>
