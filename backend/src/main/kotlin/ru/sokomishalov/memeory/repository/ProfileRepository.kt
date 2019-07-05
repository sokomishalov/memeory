package ru.sokomishalov.memeory.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import ru.sokomishalov.memeory.entity.mongo.Profile


/**
 * @author sokomishalov
 */
@Repository
interface ProfileRepository : ReactiveMongoRepository<Profile, String>
