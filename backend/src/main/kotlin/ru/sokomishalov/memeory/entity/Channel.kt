package ru.sokomishalov.memeory.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ru.sokomishalov.memeory.enums.SourceType
import ru.sokomishalov.memeory.util.EMPTY


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
