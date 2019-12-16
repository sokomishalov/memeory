package ru.sokomishalov.memeory.db.mongo.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

/**
 * @author sokomishalov
 */
@Document
data class Topic(
        @Id
        var id: String = "",
        var caption: String = ""
) 