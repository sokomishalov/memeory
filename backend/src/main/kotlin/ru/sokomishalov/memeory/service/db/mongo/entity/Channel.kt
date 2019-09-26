package ru.sokomishalov.memeory.service.db.mongo.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ru.sokomishalov.commons.core.consts.EMPTY
import ru.sokomishalov.memeory.enums.SourceType


/**
 * @author sokomishalov
 */
@Document
data class Channel(
        @Id
        var id: String = EMPTY,
        var enabled: Boolean = false,
        var sourceType: SourceType? = null,
        var name: String = EMPTY,
        var uri: String = EMPTY
)
