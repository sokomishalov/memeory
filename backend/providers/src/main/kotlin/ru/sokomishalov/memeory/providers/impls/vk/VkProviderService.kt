@file:Suppress("RemoveExplicitTypeArguments")

package ru.sokomishalov.memeory.providers.impls.vk

import org.jsoup.nodes.Element
import org.springframework.stereotype.Service
import ru.sokomishalov.commons.core.html.getImageBackgroundUrl
import ru.sokomishalov.commons.core.html.getSingleElementByClass
import ru.sokomishalov.commons.core.html.getSingleElementByTag
import ru.sokomishalov.commons.core.html.getWebPage
import ru.sokomishalov.commons.core.images.getImageAspectRatio
import ru.sokomishalov.memeory.core.dto.AttachmentDTO
import ru.sokomishalov.memeory.core.dto.ChannelDTO
import ru.sokomishalov.memeory.core.dto.MemeDTO
import ru.sokomishalov.memeory.core.enums.AttachmentType.IMAGE
import ru.sokomishalov.memeory.core.enums.AttachmentType.VIDEO
import ru.sokomishalov.memeory.core.enums.Provider
import ru.sokomishalov.memeory.core.util.consts.DELIMITER
import ru.sokomishalov.memeory.core.util.consts.VK_URL
import ru.sokomishalov.memeory.providers.ProviderService
import ru.sokomishalov.memeory.providers.util.html.removeLinks
import ru.sokomishalov.memeory.providers.util.time.mockDate
import java.util.*


@Service
class VkProviderService : ProviderService {

    override suspend fun fetchMemes(channel: ChannelDTO, limit: Int): List<MemeDTO> {
        val posts = getWebPage("$VK_URL/${channel.uri}")
                .getSingleElementByClass("wall_posts")
                .getElementsByClass("wall_item")
                .take(limit)

        return posts.mapIndexed { i, it ->
            MemeDTO(
                    id = "${channel.id}$DELIMITER${extractId(it)}",
                    caption = extractCaption(it),
                    publishedAt = extractDate(i),
                    attachments = extractAttachments(it)
            )
        }
    }

    override suspend fun getLogoUrl(channel: ChannelDTO): String? {
        return getWebPage("$VK_URL/${channel.uri}")
                .getSingleElementByClass("profile_panel")
                .getSingleElementByTag("img")
                .attr("src")
    }

    override val provider: Provider = Provider.VK

    private fun extractId(element: Element): String {
        return element
                .getElementsByAttribute("data-post-id")
                .attr("data-post-id")
                .substringAfter("_")
    }

    private fun extractCaption(element: Element): String? {
        return element
                .getElementsByClass("pi_text")
                ?.firstOrNull()
                ?.removeLinks()
    }

    private fun extractDate(i: Int): Date {
        return mockDate(i)
    }

    private suspend fun extractAttachments(element: Element): List<AttachmentDTO> {
        return element
                .getElementsByClass("thumb_map_img")
                .firstOrNull()
                .let {
                    when (it) {
                        null -> emptyList<AttachmentDTO>()
                        else -> {
                            val isVideo = it.attr("data-video").isNotBlank()
                            val imageUrl = runCatching { it.getImageBackgroundUrl() }.getOrNull()
                            val aspectRatio = imageUrl?.let { url -> getImageAspectRatio(url) }

                            listOf(AttachmentDTO(
                                    url = when {
                                        isVideo -> "$VK_URL${it.attr("href")}"
                                        else -> imageUrl
                                    },
                                    type = when {
                                        isVideo -> VIDEO
                                        else -> IMAGE
                                    },
                                    aspectRatio = aspectRatio
                            ))
                        }
                    }
                }
    }
}