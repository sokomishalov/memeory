package ru.sokomishalov.memeory.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ru.sokomishalov.memeory.util.EMPTY

@Document
data class Meme(
        @Id
        var id: String = EMPTY,
        var caption: String? = EMPTY,
        var attachments: List<Attachment> = emptyList()
)