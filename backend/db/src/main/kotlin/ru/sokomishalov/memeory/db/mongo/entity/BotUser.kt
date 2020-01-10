package ru.sokomishalov.memeory.db.mongo.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class BotUser(
        @Id
        var username: String = "",
        var fullName: String = "",
        var languageCode: String? = null,
        var chatId: Long = 0,
        val topics: List<String> = mutableListOf()
)