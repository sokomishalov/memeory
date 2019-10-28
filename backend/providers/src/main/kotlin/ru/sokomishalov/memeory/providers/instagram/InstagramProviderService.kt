package ru.sokomishalov.memeory.providers.instagram

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import me.postaddict.instagram.scraper.Instagram
import okhttp3.OkHttpClient
import org.springframework.stereotype.Service
import ru.sokomishalov.memeory.core.dto.AttachmentDTO
import ru.sokomishalov.memeory.core.dto.ChannelDTO
import ru.sokomishalov.memeory.core.dto.MemeDTO
import ru.sokomishalov.memeory.core.enums.AttachmentType.IMAGE
import ru.sokomishalov.memeory.core.enums.AttachmentType.VIDEO
import ru.sokomishalov.memeory.core.enums.Provider
import ru.sokomishalov.memeory.core.enums.Provider.INSTAGRAM
import ru.sokomishalov.memeory.core.util.consts.DELIMITER
import ru.sokomishalov.memeory.providers.ProviderService


/**
 * @author sokomishalov
 */
@Service
class InstagramProviderService(
        private val client: Instagram = Instagram(OkHttpClient())
) : ProviderService {

    override suspend fun fetchMemes(channel: ChannelDTO, limit: Int): List<MemeDTO> {
        val posts = withContext(IO) {
            client
                    .getMedias(channel.uri, 1)
                    .nodes
                    .take(limit)
        }

        return posts.map {
            MemeDTO(
                    id = "${channel.id}$DELIMITER${it.id}",
                    caption = it.caption,
                    publishedAt = it.created,
                    attachments = listOf(
                            AttachmentDTO(
                                    url = it.videoUrl ?: it.displayUrl,
                                    type = when {
                                        it.isVideo -> VIDEO
                                        else -> IMAGE
                                    },
                                    aspectRatio = it.width.toDouble().div(it.height)
                            )
                    )

            )
        }
    }

    override suspend fun getLogoUrl(channel: ChannelDTO): String? = withContext(IO) {
        client.getAccountByUsername(channel.uri).profilePicUrl
    }

    override val provider: Provider = INSTAGRAM
}
