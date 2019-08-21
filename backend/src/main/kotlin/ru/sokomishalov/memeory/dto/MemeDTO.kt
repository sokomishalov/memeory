package ru.sokomishalov.memeory.dto

import ru.sokomishalov.memeory.util.consts.EMPTY
import java.util.*

data class MemeDTO(
        var id: String = EMPTY,
        var channelId: String? = null,
        var channelName: String? = null,
        var caption: String? = null,
        var publishedAt: Date = Date(),
        var attachments: List<AttachmentDTO> = emptyList()
)
