package ru.sokomishalov.memeory.config

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories


/**
 * @author sokomishalov
 */
@EnableReactiveMongoRepositories
class MongoConfig : AbstractReactiveMongoConfiguration() {

    override fun reactiveMongoClient(): MongoClient = MongoClients.create()

    override fun getDatabaseName(): String = "memeory"
}
