@file:Suppress("ComplexRedundantLet")

package ru.sokomishalov.memeory.service.provider.ifunny

import org.springframework.stereotype.Service
import org.springframework.web.util.UriComponentsBuilder
import ru.sokomishalov.commons.core.html.getSingleElementByClass
import ru.sokomishalov.commons.core.html.getSingleElementByTag
import ru.sokomishalov.commons.core.html.getWebPage
import ru.sokomishalov.memeory.dto.AttachmentDTO
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.enums.AttachmentType.IMAGE
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
        val document = getWebPage("${IFUNNY_URL}/${channel.uri}")

        val posts = document
                .getSingleElementByClass("feed__list")
                .getElementsByClass("stream__item")

        return posts
                .mapIndexed { i, it ->
                    it.getSingleElementByTag("a").let { a ->
                        a.getSingleElementByTag("img").let { img ->
                            val link = a.attr("href")

                            // videos and gifs cannot be scraped :(
                            if ("video" in link || "gif" in link) {
                                return@mapIndexed null
                            }

                            MemeDTO(
                                    id = "${channel.id}$DELIMITER${link.convertUrlToId()}",
                                    publishedAt = mockDate(i),
                                    attachments = listOf(AttachmentDTO(
                                            url = img.attr("data-src"),
                                            type = IMAGE,
                                            aspectRatio = it.attr("data-ratio").toDoubleOrNull()?.let { 1.div(it) }
                                    ))
                            )
                        }
                    }
                }
                .filterNotNull()
    }

    override suspend fun getLogoUrl(channel: ChannelDTO): String? {
        return getWebPage("${IFUNNY_URL}/${channel.uri}")
                .getElementsByTag("meta")
                .find { it.attr("property") == "og:image" }
                ?.attr("content")
    }

    override val provider: Provider = IFUNNY

    private fun String.convertUrlToId(): String? {
        return UriComponentsBuilder
                .fromUriString(this)
                .replaceQuery(null)
                .build(emptyMap<String, String>())
                .toASCIIString()
                .replace("/", DELIMITER)
    }
}