@file:Suppress("unused")

package ru.sokomishalov.memeory.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
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
    fun customizeConversion() = mongoConverter.setMapKeyDotReplacement("_")
}
