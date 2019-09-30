package ru.sokomishalov.memeory.service.provider.facebook.scrape

import org.jsoup.nodes.Element
import org.springframework.context.annotation.Conditional
import org.springframework.stereotype.Service
import ru.sokomishalov.commons.core.collections.aMap
import ru.sokomishalov.commons.core.html.getWebPage
import ru.sokomishalov.commons.core.images.getImageAspectRatio
import ru.sokomishalov.memeory.dto.AttachmentDTO
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.enums.AttachmentType.IMAGE
import ru.sokomishalov.memeory.enums.SourceType
import ru.sokomishalov.memeory.enums.SourceType.FACEBOOK
import ru.sokomishalov.memeory.service.provider.ProviderService
import ru.sokomishalov.memeory.service.provider.facebook.FacebookCondition
import ru.sokomishalov.memeory.util.consts.DELIMITER
import ru.sokomishalov.memeory.util.consts.FACEBOOK_BASE_URL
import ru.sokomishalov.memeory.util.consts.FACEBOOK_GRAPH_BASE_URL
import java.util.*
import java.util.UUID.randomUUID


/**
 * @author sokomishalov
 */
@Service
@Conditional(FacebookCondition::class, FacebookScrapeCondition::class)
class FacebookScrapeProviderService : ProviderService {

    override suspend fun fetchMemesFromChannel(channel: ChannelDTO): List<MemeDTO> {
        val webPage = getWebPage("$FACEBOOK_BASE_URL/${channel.uri}/posts")
        val elements = webPage.getElementsByClass("userContentWrapper")

        return elements.aMap {
            MemeDTO(
                    id = "${channel.id}$DELIMITER${getIdByUserContentWrapper(it)}",
                    caption = getCaptionByUserContentWrapper(it),
                    publishedAt = getPublishedAtByUserContentWrapper(it),
                    attachments = getAttachmentsByUserContentWrapper(it)
            )
        }
    }


    override suspend fun getLogoUrlByChannel(channel: ChannelDTO): String {
        return "$FACEBOOK_GRAPH_BASE_URL/${channel.uri}/picture?type=small"
    }


    override fun sourceType(): SourceType = FACEBOOK


    private fun getIdByUserContentWrapper(contentWrapper: Element): String {
        return contentWrapper
                .getElementsByAttributeValueContaining("id", "feed_subtitle")
                ?.first()
                ?.attr("id")
                ?: randomUUID().toString()
    }


    private fun getCaptionByUserContentWrapper(contentWrapper: Element): String? {
        return contentWrapper
                .getElementsByClass("userContent")
                ?.first()
                ?.getElementsByTag("p")
                ?.first()
                ?.ownText()
                ?.toString()
    }

    private fun getPublishedAtByUserContentWrapper(contentWrapper: Element): Date {
        return contentWrapper
                .getElementsByAttribute("data-utime")
                ?.first()
                ?.attr("data-utime")
                ?.run { Date(this.toLong().times(1000)) }
                ?: Date(0)
    }

    private suspend fun getAttachmentsByUserContentWrapper(contentWrapper: Element): List<AttachmentDTO> {
        return contentWrapper
                .getElementsByClass("scaledImageFitWidth")
                ?.first()
                ?.attr("src")
                ?.let {
                    listOf(AttachmentDTO(
                            url = it,
                            type = IMAGE,
                            aspectRatio = getImageAspectRatio(it)
                    ))
                }
                ?: emptyList()
    }
}
