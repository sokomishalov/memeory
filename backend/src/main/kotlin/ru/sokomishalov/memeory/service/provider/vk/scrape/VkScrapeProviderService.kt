@file:Suppress("RemoveExplicitTypeArguments")

package ru.sokomishalov.memeory.service.provider.vk.scrape

import org.jsoup.nodes.Element
import org.springframework.context.annotation.Conditional
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.sokomishalov.memeory.dto.AttachmentDTO
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.enums.AttachmentType.IMAGE
import ru.sokomishalov.memeory.enums.AttachmentType.VIDEO
import ru.sokomishalov.memeory.enums.SourceType
import ru.sokomishalov.memeory.enums.SourceType.VK
import ru.sokomishalov.memeory.service.provider.ProviderService
import ru.sokomishalov.memeory.service.provider.vk.VkCondition
import ru.sokomishalov.memeory.util.consts.ID_DELIMITER
import ru.sokomishalov.memeory.util.consts.VK_URL
import ru.sokomishalov.memeory.util.extensions.aForEach
import ru.sokomishalov.memeory.util.extensions.aMap
import ru.sokomishalov.memeory.util.extensions.flux
import ru.sokomishalov.memeory.util.extensions.mono
import ru.sokomishalov.memeory.util.io.aGetImageAspectRatio
import ru.sokomishalov.memeory.util.log.Loggable
import ru.sokomishalov.memeory.util.scrape.fixCaption
import ru.sokomishalov.memeory.util.scrape.getImageBackgroundUrl
import ru.sokomishalov.memeory.util.scrape.getSingleElementByClass
import ru.sokomishalov.memeory.util.scrape.getWebPage
import java.lang.System.currentTimeMillis
import java.util.*


@Service
@Conditional(VkCondition::class, VkScrapeCondition::class)
class VkScrapeProviderService : ProviderService, Loggable {
    override fun fetchMemesFromChannel(channel: ChannelDTO): Flux<MemeDTO> = flux {
        val posts = getWebPage("$VK_URL/${channel.uri}")
                .getElementById("page_wall_posts")
                .getElementsByClass("post")

        val memes = posts.aMap {
            MemeDTO(
                    id = "${channel.id}$ID_DELIMITER${extractId(it)}",
                    caption = extractCaption(it),
                    publishedAt = extractDate(it),
                    attachments = extractAttachments(it)
            )
        }

        memes.aForEach { send(it) }
    }

    override fun getLogoUrlByChannel(channel: ChannelDTO): Mono<String> = mono {
        getWebPage("$VK_URL/${channel.uri}")
                .getSingleElementByClass("page_cover_image")
                .getSingleElementByClass("img")
                .attr("src")
    }

    override fun sourceType(): SourceType = VK


    private fun extractId(element: Element): String {
        return element
                .attr("data-post-id")
                .substringAfter("_")
    }

    private fun extractCaption(element: Element): String? {
        return element
                .getElementsByClass("wall_post_text")
                ?.firstOrNull()
                ?.fixCaption()
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
                    val aspectRatio = imageUrl?.let { url -> aGetImageAspectRatio(url) }

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