@file:Suppress("unused")

package ru.sokomishalov.memeory.config

import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.MongoDbFactory
import org.springframework.data.mongodb.MongoTransactionManager
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories


/**
 * @author sokomishalov
 */
@EnableReactiveMongoRepositories
class MongoConfig {

    @Bean
    fun transactionManager(dbFactory: MongoDbFactory): MongoTransactionManager = MongoTransactionManager(dbFactory)
}
