package ru.sokomishalov.memeory.service.provider.ifunny.scrape

import org.springframework.context.annotation.Conditional
import org.springframework.stereotype.Service
import ru.sokomishalov.commons.core.html.getSingleElementByClass
import ru.sokomishalov.commons.core.html.getWebPage
import ru.sokomishalov.memeory.dto.AttachmentDTO
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.enums.AttachmentType.IMAGE
import ru.sokomishalov.memeory.enums.AttachmentType.VIDEO
import ru.sokomishalov.memeory.enums.SourceType
import ru.sokomishalov.memeory.enums.SourceType.IFUNNY
import ru.sokomishalov.memeory.service.provider.ProviderService
import ru.sokomishalov.memeory.service.provider.ifunny.IFunnyCondition
import ru.sokomishalov.memeory.util.consts.DELIMITER
import ru.sokomishalov.memeory.util.consts.IFUNNY_URL

/**
 * @author sokomishalov
 */
@Service
@Conditional(IFunnyCondition::class, IFunnyScrapeCondition::class)
class IFunnyProviderService : ProviderService {

    override suspend fun fetchMemesFromChannel(channel: ChannelDTO): List<MemeDTO> {
        val posts = getWebPage("${IFUNNY_URL}/${channel.uri}")
                .getSingleElementByClass("feed__list")
                .getElementsByClass("stream__item")

        return posts
                .map {
                    it.getElementsByTag("a").first().let { a ->
                        a.getElementsByTag("img").first().let { img ->
                            MemeDTO(
                                    id = "${channel.id}$DELIMITER${a.attr("href").replace("/", DELIMITER)}",
                                    attachments = listOf(AttachmentDTO(
                                            url = img.attr("data-src"),
                                            type = when {
                                                "video" in a.attr("href") -> VIDEO
                                                else -> IMAGE
                                            },
                                            aspectRatio = it.attr("data-ratio").toDoubleOrNull()
                                    ))
                            )
                        }
                    }
                }
    }

    override suspend fun getLogoUrlByChannel(channel: ChannelDTO): String? {
        return getWebPage("${IFUNNY_URL}/${channel.uri}")
                .getElementsByTag("meta")
                .find { it.attr("property") == "og:image" }
                ?.attr("content")
    }

    override fun sourceType(): SourceType = IFUNNY
}