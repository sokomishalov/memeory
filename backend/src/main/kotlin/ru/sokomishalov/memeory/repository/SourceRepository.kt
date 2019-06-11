package ru.sokomishalov.memeory.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import ru.sokomishalov.memeory.entity.Source


/**
 * @author sokomishalov
 */
interface SourceRepository : ReactiveMongoRepository<Source, String>
