package ru.sokomishalov.memeory.db.mongo.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ru.sokomishalov.memeory.core.enums.Provider


/**
 * @author sokomishalov
 */
@Document
data class Channel(
        @Id
        var id: String = "",
        var enabled: Boolean = false,
        var provider: Provider? = null,
        var name: String = "",
        var uri: String = "",
        var topics: List<String> = emptyList()
)
