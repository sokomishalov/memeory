package ru.sokomishalov.memeory.core.dto

import ru.sokomishalov.commons.core.consts.EMPTY
import java.util.*

data class MemeDTO(
        var id: String = EMPTY,
        var channelId: String? = null,
        var caption: String? = null,
        var publishedAt: Date = Date(),
        var attachments: List<AttachmentDTO> = emptyList()
)
