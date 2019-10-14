package ru.sokomishalov.memeory.service.provider.twitter

import org.jsoup.nodes.Element
import org.springframework.stereotype.Service
import ru.sokomishalov.commons.core.collections.aMap
import ru.sokomishalov.commons.core.html.fixText
import ru.sokomishalov.commons.core.html.getSingleElementByClass
import ru.sokomishalov.commons.core.html.getWebPage
import ru.sokomishalov.commons.core.images.getImageAspectRatio
import ru.sokomishalov.memeory.dto.AttachmentDTO
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.enums.AttachmentType.IMAGE
import ru.sokomishalov.memeory.enums.Provider
import ru.sokomishalov.memeory.enums.Provider.TWITTER
import ru.sokomishalov.memeory.service.provider.ProviderService
import ru.sokomishalov.memeory.util.consts.DELIMITER
import ru.sokomishalov.memeory.util.consts.TWITTER_URL
import java.util.*


/**
 * @author sokomishalov
 */
@Service
class TwitterProviderService : ProviderService {

    override suspend fun fetchMemes(channel: ChannelDTO): List<MemeDTO> {
        val webPage = getWebPage("$TWITTER_URL/${channel.uri}")

        val posts = webPage
                .body()
                .getElementById("stream-items-id")
                .getElementsByClass("stream-item")

        return posts
                .map {
                    it.getSingleElementByClass("tweet")
                }
                .aMap {
                    MemeDTO(
                            id = "${channel.id}$DELIMITER${extractIdFromTweet(it)}",
                            caption = extractCaptionFromTweet(it),
                            publishedAt = extractPublishedAtFromTweet(it),
                            attachments = extractAttachmentsFromTweet(it)
                    )
                }
    }

    override suspend fun getLogoUrl(channel: ChannelDTO): String? {
        return getWebPage("$TWITTER_URL/${channel.uri}")
                .body()
                .getSingleElementByClass("ProfileAvatar-image")
                .attr("src")
    }

    override val provider: Provider = TWITTER

    private fun extractIdFromTweet(tweet: Element): String {
        return tweet
                .getSingleElementByClass("js-stream-tweet")
                .attr("data-tweet-id")
    }

    private suspend fun extractCaptionFromTweet(tweet: Element): String? {
        return tweet
                .getSingleElementByClass("tweet-text")
                .fixText()
    }

    private fun extractPublishedAtFromTweet(tweet: Element): Date {
        return try {
            tweet
                    .getSingleElementByClass("js-short-timestamp")
                    .attr("data-time-ms")
                    .toLong()
                    .let { Date(it) }
        } catch (ex: NumberFormatException) {
            null
        } ?: Date(0)
    }

    private suspend fun extractAttachmentsFromTweet(tweet: Element): List<AttachmentDTO> {
        return tweet
                .getElementsByClass("AdaptiveMedia-photoContainer")
                ?.map { element ->
                    element.attr("data-image-url").let {
                        AttachmentDTO(
                                url = it,
                                type = IMAGE,
                                aspectRatio = getImageAspectRatio(it)
                        )
                    }
                }
                ?: emptyList()
    }
}
