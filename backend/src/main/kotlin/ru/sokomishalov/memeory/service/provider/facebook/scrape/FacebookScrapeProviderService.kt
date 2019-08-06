package ru.sokomishalov.memeory.service.provider.facebook.scrape

import org.jsoup.Jsoup.connect
import org.jsoup.nodes.Element
import org.springframework.context.annotation.Conditional
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Flux.fromIterable
import reactor.core.publisher.Mono
import ru.sokomishalov.memeory.dto.AttachmentDTO
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.enums.AttachmentType.IMAGE
import ru.sokomishalov.memeory.enums.SourceType
import ru.sokomishalov.memeory.enums.SourceType.FACEBOOK
import ru.sokomishalov.memeory.service.provider.ProviderService
import ru.sokomishalov.memeory.service.provider.facebook.FacebookCondition
import ru.sokomishalov.memeory.util.FACEBOOK_BASE_URl
import ru.sokomishalov.memeory.util.FACEBOOK_GRAPH_BASE_URl
import ru.sokomishalov.memeory.util.ID_DELIMITER
import ru.sokomishalov.memeory.util.io.getImageAspectRatio
import java.util.*
import java.util.UUID.randomUUID
import reactor.core.publisher.Mono.just as monoJust


/**
 * @author sokomishalov
 */
@Service
@Conditional(FacebookCondition::class, FacebookScrapeCondition::class)
class FacebookScrapeProviderService : ProviderService {

    override fun fetchMemesFromChannel(channel: ChannelDTO): Flux<MemeDTO> {
        return monoJust(channel)
                .map { connect("$FACEBOOK_BASE_URl/${it.uri}/posts").get() }
                .map { it.getElementsByClass("userContentWrapper") }
                .flatMapMany { fromIterable(it) }
                .map {
                    MemeDTO(
                            id = "${channel.id}$ID_DELIMITER${getIdByUserContentWrapper(it)}",
                            caption = getCaptionByUserContentWrapper(it),
                            publishedAt = getPublishedAtByUserContentWrapper(it),
                            attachments = getAttachmentsByUserContentWrapper(it)
                    )
                }
    }

    override fun getLogoUrlByChannel(channel: ChannelDTO): Mono<String> {
        return monoJust("$FACEBOOK_GRAPH_BASE_URl/${channel.uri}/picture?type=small")
    }

    override fun sourceType(): SourceType = FACEBOOK


    private fun getIdByUserContentWrapper(contentWrapper: Element?): String {
        return contentWrapper
                ?.getElementsByAttributeValueContaining("id", "feed_subtitle")
                ?.first()
                ?.attr("id")
                ?: randomUUID().toString()
    }


    private fun getCaptionByUserContentWrapper(contentWrapper: Element?): String? {
        return contentWrapper
                ?.getElementsByClass("userContent")
                ?.first()
                ?.getElementsByTag("p")
                ?.first()
                ?.ownText()
                ?.toString()
    }

    private fun getPublishedAtByUserContentWrapper(contentWrapper: Element?): Date {
        return contentWrapper
                ?.getElementsByAttribute("data-utime")
                ?.first()
                ?.attr("data-utime")
                ?.run { Date(this.toLong().times(1000)) }
                ?: Date(0)
    }

    private fun getAttachmentsByUserContentWrapper(contentWrapper: Element?): List<AttachmentDTO> {
        return contentWrapper
                ?.getElementsByClass("scaledImageFitWidth")
                ?.first()
                ?.attr("src")
                ?.let {
                    listOf(AttachmentDTO(
                            url = it,
                            type = IMAGE,
                            aspectRatio = getImageAspectRatio(it)
                    ))
                }
                ?: emptyList()
    }
}
