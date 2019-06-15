package ru.sokomishalov.memeory.dto

import ru.sokomishalov.memeory.util.EMPTY

data class MemeDTO(
        var id: String = EMPTY,
        var caption: String? = EMPTY,
        var attachments: List<AttachmentDTO> = emptyList()
)