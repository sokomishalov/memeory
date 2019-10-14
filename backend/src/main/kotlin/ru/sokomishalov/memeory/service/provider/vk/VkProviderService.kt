@file:Suppress("RemoveExplicitTypeArguments")

package ru.sokomishalov.memeory.service.provider.vk

import org.jsoup.nodes.Element
import org.springframework.stereotype.Service
import ru.sokomishalov.commons.core.collections.aMap
import ru.sokomishalov.commons.core.html.fixText
import ru.sokomishalov.commons.core.html.getImageBackgroundUrl
import ru.sokomishalov.commons.core.html.getSingleElementByClass
import ru.sokomishalov.commons.core.html.getWebPage
import ru.sokomishalov.commons.core.images.getImageAspectRatio
import ru.sokomishalov.commons.core.log.Loggable
import ru.sokomishalov.memeory.dto.AttachmentDTO
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.enums.AttachmentType.IMAGE
import ru.sokomishalov.memeory.enums.AttachmentType.VIDEO
import ru.sokomishalov.memeory.enums.Provider
import ru.sokomishalov.memeory.enums.Provider.VK
import ru.sokomishalov.memeory.service.provider.ProviderService
import ru.sokomishalov.memeory.util.consts.DELIMITER
import ru.sokomishalov.memeory.util.consts.VK_URL
import java.lang.System.currentTimeMillis
import java.util.*


@Service
class VkProviderService : ProviderService, Loggable {
    override suspend fun fetchMemes(channel: ChannelDTO): List<MemeDTO> {
        val posts = getWebPage("$VK_URL/${channel.uri}")
                .getElementById("page_wall_posts")
                .getElementsByClass("post")

        return posts.aMap {
            MemeDTO(
                    id = "${channel.id}$DELIMITER${extractId(it)}",
                    caption = extractCaption(it),
                    publishedAt = extractDate(it),
                    attachments = extractAttachments(it)
            )
        }
    }

    override suspend fun getLogoUrl(channel: ChannelDTO): String? {
        return getWebPage("$VK_URL/${channel.uri}")
                .getSingleElementByClass("page_cover_image")
                .getElementsByTag("img")
                .first()
                .attr("src")
    }

    override val provider: Provider = VK

    private fun extractId(element: Element): String {
        return element
                .attr("data-post-id")
                .substringAfter("_")
    }

    private suspend fun extractCaption(element: Element): String? {
        return element
                .getElementsByClass("wall_post_text")
                ?.firstOrNull()
                ?.fixText()
    }

    private fun extractDate(element: Element): Date {
        return element
                .getElementsByAttribute("time")
                .firstOrNull()
                ?.attr("time")
                .let {
                    val ts = when {
                        it.isNullOrBlank() -> currentTimeMillis()
                        else -> it.toLong() * 1000
                    }
                    Date(ts)
                }
    }


    private suspend fun extractAttachments(element: Element): List<AttachmentDTO> {
        return element
                .getSingleElementByClass("page_post_sized_thumbs")
                .getElementsByTag("a")
                .aMap {
                    val isVideo = it.attr("data-video").isNotBlank()
                    val imageUrl = runCatching { it.getImageBackgroundUrl() }.getOrNull()
                    val aspectRatio = imageUrl?.let { url -> getImageAspectRatio(url) }

                    AttachmentDTO(
                            url = when {
                                isVideo -> "$VK_URL${it.attr("href")}"
                                else -> imageUrl
                            },
                            type = when {
                                isVideo -> VIDEO
                                else -> IMAGE
                            },
                            aspectRatio = aspectRatio
                    )
                }
    }
}