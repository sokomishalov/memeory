package ru.sokomishalov.memeory.db.mongo.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

/**
 * @author sokomishalov
 */
@Document
data class SocialAccount(
        @Id
        var uid: String? = null,
        var displayName: String? = null,
        var photoURL: String? = null,
        var email: String? = null
)