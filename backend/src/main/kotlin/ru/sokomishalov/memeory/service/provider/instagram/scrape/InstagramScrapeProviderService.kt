package ru.sokomishalov.memeory.service.provider.instagram.scrape

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import me.postaddict.instagram.scraper.Instagram
import org.springframework.context.annotation.Conditional
import org.springframework.stereotype.Service
import ru.sokomishalov.commons.core.collections.aMap
import ru.sokomishalov.memeory.dto.AttachmentDTO
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.enums.AttachmentType.IMAGE
import ru.sokomishalov.memeory.enums.AttachmentType.VIDEO
import ru.sokomishalov.memeory.enums.SourceType
import ru.sokomishalov.memeory.enums.SourceType.INSTAGRAM
import ru.sokomishalov.memeory.service.provider.ProviderService
import ru.sokomishalov.memeory.service.provider.instagram.InstagramCondition
import ru.sokomishalov.memeory.util.consts.ID_DELIMITER


/**
 * @author sokomishalov
 */
@Service
@Conditional(InstagramCondition::class, InstagramScrapeCondition::class)
class InstagramScrapeProviderService(private val instagram: Instagram) : ProviderService {

    override suspend fun fetchMemesFromChannel(channel: ChannelDTO): List<MemeDTO> {
        val posts = withContext(IO) {
            instagram.getMedias(channel.uri, 1).nodes
        }

        return posts.aMap {
            MemeDTO(
                    id = "${channel.id}$ID_DELIMITER${it.id}",
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

    override suspend fun getLogoUrlByChannel(channel: ChannelDTO): String = withContext(IO) {
        instagram.getAccountByUsername(channel.uri).profilePicUrl
    }

    override fun sourceType(): SourceType = INSTAGRAM
}
