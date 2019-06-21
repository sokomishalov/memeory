package ru.sokomishalov.memeory.entity

import ru.sokomishalov.memeory.enums.AttachmentType
import ru.sokomishalov.memeory.enums.AttachmentType.NONE
import ru.sokomishalov.memeory.util.EMPTY

data class Attachment(
        var url: String? = EMPTY,
        var type: AttachmentType? = NONE
)
