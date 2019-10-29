package ru.sokomishalov.memeory.db.mongo.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ru.sokomishalov.commons.core.consts.EMPTY

@Document
data class BotUser(
        @Id
        var username: String = EMPTY,
        var fullName: String = EMPTY,
        var languageCode: String? = null,
        var chatId: Long = 0,
        var memeoryId: String? = null
)