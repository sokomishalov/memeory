package ru.sokomishalov.memeory.service.provider.twitter.scrape

import org.jsoup.Jsoup.connect
import org.jsoup.Jsoup.parse
import org.jsoup.nodes.Element
import org.springframework.context.annotation.Conditional
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
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
import ru.sokomishalov.memeory.util.ID_DELIMITER
import ru.sokomishalov.memeory.util.TWITTER_URL
import ru.sokomishalov.memeory.util.getImageAspectRatio
import ru.sokomishalov.memeory.util.getImageByteArrayMonoByUrl
import java.util.*
import java.util.UUID.randomUUID
import kotlin.collections.ArrayList


/**
 * @author sokomishalov
 */
@Service
@Conditional(TwitterCondition::class, TwitterScrapeCondition::class)
class TwitterScrapeProviderService(
        private val webClient: WebClient
) : ProviderService {

    override fun fetchMemesFromChannel(channel: ChannelDTO): Flux<MemeDTO> {
        return just(channel)
                .map { connect("$TWITTER_URL/${it.uri}").get() }
                .map { it.body().getElementById("stream-items-id") }
                .map { it.getElementsByClass("stream-item") }
                .flatMapMany { fromIterable(it) }
                .map { it.getElementsByClass("tweet").first() }
                .map {
                    MemeDTO(
                            id = "${channel.id}$ID_DELIMITER${getIdByTweet(it)}",
                            caption = getCaptionByTweet(it),
                            publishedAt = getPublishedAtByTweet(it),
                            attachments = getAttachmentsByTweet(it)
                    )
                }
    }

    override fun getLogoByChannel(channel: ChannelDTO): Mono<ByteArray> {
        return just(channel)
                .map { connect("$TWITTER_URL/${it.uri}").get() }
                .map { it.body().getElementsByClass("ProfileAvatar-image").first() }
                .map { it.attr("src") }
                .flatMap { getImageByteArrayMonoByUrl(it, webClient) }
    }

    override fun sourceType(): SourceType = TWITTER


    private fun getIdByTweet(tweet: Element?): String {
        return tweet
                ?.getElementsByClass("js-stream-tweet")
                ?.first()
                ?.attr("data-tweet-id")
                ?: randomUUID().toString()

    }

    private fun getCaptionByTweet(tweet: Element?): String? {
        val titleDoc = tweet
                ?.getElementsByClass("tweet-text")
                ?.first()
                ?.html()
                .let { parse(it) }

        val allAnchors = titleDoc.select("a")
        val twitterAnchors = titleDoc.select("a[href^=/]")
        val unwantedAnchors = ArrayList<Element>()

        allAnchors.filterNotTo(unwantedAnchors) { twitterAnchors.contains(it) }
        unwantedAnchors.forEach { it.remove() }

        return titleDoc.text()
    }

    private fun getPublishedAtByTweet(tweet: Element?): Date {
        return try {
            tweet
                    ?.getElementsByClass("js-short-timestamp")
                    ?.first()
                    ?.attr("data-time-ms")
                    ?.toLong()
                    ?.let { Date(it) }
        } catch (ex: NumberFormatException) {
            null
        } ?: Date(0)
    }

    private fun getAttachmentsByTweet(tweet: Element?): List<AttachmentDTO> {
        return tweet
                ?.getElementsByClass("AdaptiveMedia-photoContainer")
                ?.map { e ->
                    e
                            .attr("data-image-url")
                            .let {
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
