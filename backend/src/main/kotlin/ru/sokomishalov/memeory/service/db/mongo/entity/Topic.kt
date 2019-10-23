package ru.sokomishalov.memeory.service.db.mongo.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ru.sokomishalov.commons.core.consts.EMPTY

/**
 * @author sokomishalov
 */
@Document
data class Topic(
        @Id
        var id: String = EMPTY,
        var name: String = EMPTY
)