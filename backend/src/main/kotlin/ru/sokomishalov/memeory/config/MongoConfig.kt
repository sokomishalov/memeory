@file:Suppress("unused")

package ru.sokomishalov.memeory.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import ru.sokomishalov.memeory.util.consts.MONGO_KEY_DOT_REPLACEMENT
import javax.annotation.PostConstruct


/**
 * @author sokomishalov
 */
@Configuration
@EnableReactiveMongoRepositories("ru.sokomishalov.memeory.service.db.mongo.repository")
class MongoConfig(
        private val mongoConverter: MappingMongoConverter
) {

    @PostConstruct
    fun customizeConversion() = mongoConverter.setMapKeyDotReplacement(MONGO_KEY_DOT_REPLACEMENT)
}
