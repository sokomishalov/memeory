package ru.sokomishalov.memeory.db.mongo.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import ru.sokomishalov.memeory.db.mongo.entity.Profile


/**
 * @author sokomishalov
 */
@Repository
interface ProfileRepository : ReactiveMongoRepository<Profile, String>
