package ru.sokomishalov.memeory.providers

import ru.sokomishalov.memeory.core.dto.AttachmentDTO
import ru.sokomishalov.memeory.core.dto.ChannelDTO
import ru.sokomishalov.memeory.core.dto.MemeDTO
import ru.sokomishalov.memeory.core.enums.AttachmentType
import ru.sokomishalov.memeory.core.util.consts.DELIMITER
import ru.sokomishalov.skraper.Skraper
import ru.sokomishalov.skraper.getPageLogoByteArray
import ru.sokomishalov.skraper.model.AttachmentType.IMAGE
import ru.sokomishalov.skraper.model.AttachmentType.VIDEO
import ru.sokomishalov.skraper.model.GetLatestPostsOptions
import ru.sokomishalov.skraper.model.GetPageLogoUrlOptions
import java.time.Duration
import java.util.*

/**
 * @author sokomishalov
 */

suspend fun Skraper.fetchMemes(channel: ChannelDTO, limit: Int = 100): List<MemeDTO> {
    val options = GetLatestPostsOptions(uri = channel.uri, limit = limit, fetchAspectRatio = true)
    return getLatestPosts(options).mapIndexed { index, post ->
        MemeDTO(
                id = "${channel.id}${DELIMITER}${post.id}",
                channelId = channel.id,
                publishedAt = Date(post.publishTimestamp ?: mockTimestamp(index = index)),
                caption = post.caption,
                attachments = post.attachments.map { a ->
                    AttachmentDTO(
                            url = a.url,
                            type = when (a.type) {
                                IMAGE -> AttachmentType.IMAGE
                                VIDEO -> AttachmentType.VIDEO
                            },
                            aspectRatio = a.aspectRatio
                    )
                }
        )
    }
}

suspend fun Skraper.getChannelLogoByteArray(channel: ChannelDTO): ByteArray? {
    val options = GetPageLogoUrlOptions(uri = channel.uri)
    return getPageLogoByteArray(options)
}


private fun mockTimestamp(index: Int = 0, from: Long = System.currentTimeMillis(), minusMultiply: Duration = Duration.ofHours(1)): Long {
    return from - (minusMultiply.multipliedBy(index.toLong())).toMillis()
}


