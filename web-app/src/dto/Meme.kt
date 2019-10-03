@file:Suppress("ArrayInDataClass")

package dto

import kotlin.js.Date

data class Meme(
        var id: String = "",
        var channelId: String? = null,
        var channelName: String? = null,
        var caption: String? = null,
        var publishedAt: Date = Date(),
        var attachments: Array<Attachment> = emptyArray()
)
