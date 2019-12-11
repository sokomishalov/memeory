@file:Suppress("ComplexRedundantLet")

package ru.sokomishalov.memeory.providers.impls.ifunny

import org.springframework.stereotype.Service
import org.springframework.web.util.UriComponentsBuilder
import ru.sokomishalov.commons.core.html.getSingleElementByClass
import ru.sokomishalov.commons.core.html.getSingleElementByTag
import ru.sokomishalov.commons.core.html.getWebPage
import ru.sokomishalov.memeory.core.dto.AttachmentDTO
import ru.sokomishalov.memeory.core.dto.ChannelDTO
import ru.sokomishalov.memeory.core.dto.MemeDTO
import ru.sokomishalov.memeory.core.enums.AttachmentType.IMAGE
import ru.sokomishalov.memeory.core.enums.Provider
import ru.sokomishalov.memeory.core.enums.Provider.IFUNNY
import ru.sokomishalov.memeory.core.util.consts.DELIMITER
import ru.sokomishalov.memeory.core.util.consts.IFUNNY_URL
import ru.sokomishalov.memeory.providers.ProviderService
import ru.sokomishalov.memeory.providers.util.time.mockDate

/**
 * @author sokomishalov
 */
@Service
class IFunnyProviderService : ProviderService {

    override suspend fun fetchMemes(channel: ChannelDTO, limit: Int): List<MemeDTO> {
        val document = getWebPage("${IFUNNY_URL}/${channel.uri}")

        val posts = document
                .getSingleElementByClass("feed__list")
                .getElementsByClass("stream__item")
                .take(limit)

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