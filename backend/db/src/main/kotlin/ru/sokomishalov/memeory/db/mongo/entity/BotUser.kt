package ru.sokomishalov.memeory.db.mongo.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ru.sokomishalov.commons.core.consts.EMPTY

@Document
data class BotUser(
        @Id
        val id: String = EMPTY,
        val fullName: String = EMPTY,
        val username: String = EMPTY,
        val languageCode: String? = null,
        val chatId: Long = 0,
        val memeoryId: String? = null
)