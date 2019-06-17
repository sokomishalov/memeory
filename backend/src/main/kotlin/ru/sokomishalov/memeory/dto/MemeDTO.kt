package ru.sokomishalov.memeory.dto

import ru.sokomishalov.memeory.util.EMPTY
import java.util.*

data class MemeDTO(
        var id: String = EMPTY,
        var caption: String? = null,
        var channel: String? = null,
        var publishedAt: Date = Date(),
        var attachments: List<AttachmentDTO>? = null
)
