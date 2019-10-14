@file:Suppress("ComplexRedundantLet")

package ru.sokomishalov.memeory.service.provider.ifunny

import org.springframework.stereotype.Service
import ru.sokomishalov.commons.core.html.getSingleElementByClass
import ru.sokomishalov.commons.core.html.getSingleElementByTag
import ru.sokomishalov.commons.core.html.getWebPage
import ru.sokomishalov.memeory.dto.AttachmentDTO
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.enums.AttachmentType.IMAGE
import ru.sokomishalov.memeory.enums.AttachmentType.VIDEO
import ru.sokomishalov.memeory.enums.Provider
import ru.sokomishalov.memeory.enums.Provider.IFUNNY
import ru.sokomishalov.memeory.service.provider.ProviderService
import ru.sokomishalov.memeory.util.consts.DELIMITER
import ru.sokomishalov.memeory.util.consts.IFUNNY_URL
import ru.sokomishalov.memeory.util.time.mockDate

/**
 * @author sokomishalov
 */
@Service
class IFunnyProviderService : ProviderService {

    override suspend fun fetchMemes(channel: ChannelDTO): List<MemeDTO> {
        val posts = getWebPage("${IFUNNY_URL}/${channel.uri}")
                .getSingleElementByClass("feed__list")
                .getElementsByClass("stream__item")

        return posts
                .mapIndexed { i, it ->
                    it.getSingleElementByTag("a").let { a ->
                        a.getSingleElementByTag("img").let { img ->
                            MemeDTO(
                                    id = "${channel.id}$DELIMITER${a.attr("href").replace("/", DELIMITER)}",
                                    publishedAt = mockDate(i),
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

    override suspend fun getLogoUrl(channel: ChannelDTO): String? {
        return getWebPage("${IFUNNY_URL}/${channel.uri}")
                .getElementsByTag("meta")
                .find { it.attr("property") == "og:image" }
                ?.attr("content")
    }

    override val provider: Provider = IFUNNY
}