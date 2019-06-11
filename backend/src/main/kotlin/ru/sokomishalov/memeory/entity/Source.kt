package ru.sokomishalov.memeory.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


/**
 * @author sokomishalov
 */
@Document
data class Source(
        @Id var id: String = "",
        var enabled: Boolean = false,
        var name: String = "",
        var url: String = ""
)
