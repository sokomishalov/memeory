package ru.sokomishalov.memeory.service.provider.instagram

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import me.postaddict.instagram.scraper.Instagram
import okhttp3.OkHttpClient
import org.springframework.stereotype.Service
import ru.sokomishalov.commons.core.collections.aMap
import ru.sokomishalov.memeory.dto.AttachmentDTO
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.enums.AttachmentType.IMAGE
import ru.sokomishalov.memeory.enums.AttachmentType.VIDEO
import ru.sokomishalov.memeory.enums.Provider
import ru.sokomishalov.memeory.enums.Provider.INSTAGRAM
import ru.sokomishalov.memeory.service.provider.ProviderService
import ru.sokomishalov.memeory.util.consts.DELIMITER


/**
 * @author sokomishalov
 */
@Service
class InstagramProviderService : ProviderService {

    companion object {
        private val client: Instagram = Instagram(OkHttpClient())
    }

    override suspend fun fetchMemes(channel: ChannelDTO): List<MemeDTO> {
        val posts = withContext(IO) {
            client.getMedias(channel.uri, 1).nodes
        }

        return posts.aMap {
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
