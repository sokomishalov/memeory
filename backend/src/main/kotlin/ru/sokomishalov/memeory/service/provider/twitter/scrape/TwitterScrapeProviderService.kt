package ru.sokomishalov.memeory.service.provider.twitter.scrape

import org.jsoup.Jsoup.connect
import org.jsoup.nodes.Element
import org.springframework.context.annotation.Conditional
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Flux.fromIterable
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import ru.sokomishalov.memeory.dto.AttachmentDTO
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.enums.AttachmentType.IMAGE
import ru.sokomishalov.memeory.enums.SourceType
import ru.sokomishalov.memeory.enums.SourceType.TWITTER
import ru.sokomishalov.memeory.service.provider.ProviderService
import ru.sokomishalov.memeory.service.provider.twitter.TwitterCondition
import ru.sokomishalov.memeory.util.consts.ID_DELIMITER
import ru.sokomishalov.memeory.util.consts.TWITTER_URL
import ru.sokomishalov.memeory.util.io.getImageAspectRatio
import ru.sokomishalov.memeory.util.scrape.fixCaption
import ru.sokomishalov.memeory.util.scrape.getSingleElementByClass
import java.util.*


/**
 * @author sokomishalov
 */
@Service
@Conditional(TwitterCondition::class, TwitterScrapeCondition::class)
// TODO rewrite
class TwitterScrapeProviderService : ProviderService {

    override fun fetchMemesFromChannel(channel: ChannelDTO): Flux<MemeDTO> {
        return just(channel)
                .map { connect("$TWITTER_URL/${it.uri}").get() }
                .map { it.body().getElementById("stream-items-id") }
                .map { it.getElementsByClass("stream-item") }
                .flatMapMany { fromIterable(it) }
                .map { it.getSingleElementByClass("tweet") }
                .map {
                    MemeDTO(
                            id = "${channel.id}$ID_DELIMITER${extractIdFromTweet(it)}",
                            caption = extractCaptionFromTweet(it),
                            publishedAt = extractPublishedAtFromTweet(it),
                            attachments = extractAttachmentsFromTweet(it)
                    )
                }
    }

    override fun getLogoUrlByChannel(channel: ChannelDTO): Mono<String> {
        return just(channel)
                .map { connect("$TWITTER_URL/${it.uri}").get() }
                .map { it.body().getSingleElementByClass("ProfileAvatar-image") }
                .map { it.attr("src") }
    }

    override fun sourceType(): SourceType = TWITTER


    private fun extractIdFromTweet(tweet: Element): String {
        return tweet
                .getSingleElementByClass("js-stream-tweet")
                .attr("data-tweet-id")
    }

    private fun extractCaptionFromTweet(tweet: Element): String? {
        return tweet
                .getSingleElementByClass("tweet-text")
                .fixCaption()
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

    private fun extractAttachmentsFromTweet(tweet: Element): List<AttachmentDTO> {
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
