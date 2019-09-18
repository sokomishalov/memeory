package ru.sokomishalov.memeory.entity.mongo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ru.sokomishalov.commons.core.consts.EMPTY
import java.util.*

@Document
data class Meme(
        @Id
        var id: String = EMPTY,
        var caption: String? = null,
        var channelId: String? = null,
        var channelName: String? = null,
        var attachments: List<Attachment> = emptyList(),
        var publishedAt: Date = Date(),
        var createdAt: Date = Date()
)
