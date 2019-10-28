package ru.sokomishalov.memeory.db.mongo.entity

import ru.sokomishalov.commons.core.consts.EMPTY
import ru.sokomishalov.memeory.core.enums.AttachmentType
import ru.sokomishalov.memeory.core.enums.AttachmentType.NONE

data class Attachment(
        var url: String? = EMPTY,
        var type: AttachmentType? = NONE,
        var aspectRatio: Double? = null
)
