package dto

import dto.AttachmentType.NONE

data class Attachment(
        var url: String? = "",
        var type: AttachmentType? = NONE,
        var aspectRatio: Double? = null
)