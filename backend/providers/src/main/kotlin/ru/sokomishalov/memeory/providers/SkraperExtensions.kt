package ru.sokomishalov.memeory.providers

import ru.sokomishalov.memeory.core.dto.AttachmentDTO
import ru.sokomishalov.memeory.core.dto.ChannelDTO
import ru.sokomishalov.memeory.core.dto.MemeDTO
import ru.sokomishalov.memeory.core.enums.AttachmentType.IMAGE
import ru.sokomishalov.memeory.core.enums.AttachmentType.VIDEO
import ru.sokomishalov.memeory.core.util.consts.DELIMITER
import ru.sokomishalov.skraper.Skraper
import ru.sokomishalov.skraper.fetchBytes
import ru.sokomishalov.skraper.model.Audio
import ru.sokomishalov.skraper.model.Image
import ru.sokomishalov.skraper.model.MediaSize.SMALL
import ru.sokomishalov.skraper.model.Video
import java.time.Duration
import java.util.*

/**
 * @author sokomishalov
 */

suspend fun Skraper.fetchMemes(channel: ChannelDTO, limit: Int = 100): List<MemeDTO> {
    return getPosts(path = channel.uri, limit = limit).mapIndexed { index, post ->
        MemeDTO(
                id = "${channel.id}${DELIMITER}${post.id}",
                channelId = channel.id,
                publishedAt = Date(post.publishedAt?.times(1000) ?: mockTimestamp(index = index)),
                caption = post.text,
                attachments = post.media.mapNotNull { a ->
                    when (a) {
                        is Image -> AttachmentDTO(
                                url = a.url,
                                type = IMAGE,
                                aspectRatio = a.aspectRatio
                        )
                        is Video -> AttachmentDTO(
                                url = a.url,
                                type = VIDEO,
                                aspectRatio = a.aspectRatio
                        )
                        is Audio -> null
                    }
                }
        )
    }
}

suspend fun Skraper.getChannelLogoByteArray(channel: ChannelDTO): ByteArray? {
    return getPageInfo(path = channel.uri)
            ?.avatarsMap
            ?.get(SMALL)
            ?.url
            ?.let { client.fetchBytes(url = it) }
}

suspend fun Skraper.getProviderLogoByteArray(): ByteArray? {
    return getProviderInfo()
            ?.logoMap
            ?.get(SMALL)
            ?.url
            ?.let { client.fetchBytes(url = it) }
}


private fun mockTimestamp(index: Int = 0, from: Long = System.currentTimeMillis(), minusMultiply: Duration = Duration.ofHours(1)): Long {
    return from - (minusMultiply.multipliedBy(index.toLong())).toMillis()
}


