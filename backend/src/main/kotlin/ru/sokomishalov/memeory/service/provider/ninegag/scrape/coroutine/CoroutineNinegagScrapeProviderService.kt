package ru.sokomishalov.memeory.service.provider.ninegag.scrape.coroutine

import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.reactor.flux
import kotlinx.coroutines.reactor.mono
import org.springframework.context.annotation.Conditional
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.sokomishalov.memeory.condition.ConditionalOnUsingCoroutines
import ru.sokomishalov.memeory.dto.AttachmentDTO
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.enums.AttachmentType.IMAGE
import ru.sokomishalov.memeory.enums.SourceType
import ru.sokomishalov.memeory.enums.SourceType.NINEGAG
import ru.sokomishalov.memeory.service.provider.ProviderService
import ru.sokomishalov.memeory.service.provider.ninegag.NinegagCondition
import ru.sokomishalov.memeory.service.provider.ninegag.scrape.NinegagScrapeCondition
import ru.sokomishalov.memeory.util.EMPTY
import ru.sokomishalov.memeory.util.ID_DELIMITER
import ru.sokomishalov.memeory.util.NINEGAG_URL
import ru.sokomishalov.memeory.util.extensions.coMap
import ru.sokomishalov.memeory.util.io.coGetImageAspectRatio
import ru.sokomishalov.memeory.util.log.Loggable
import ru.sokomishalov.memeory.util.scrape.getWebPage
import ru.sokomishalov.memeory.util.serialization.OBJECT_MAPPER
import java.util.*
import java.time.ZonedDateTime.parse as zonedDateTimeParse
import java.util.Date.from as dateFrom


/**
 * @author sokomishalov
 */
@Service
@Conditional(NinegagCondition::class, NinegagScrapeCondition::class)
@ConditionalOnUsingCoroutines
class CoroutineNinegagScrapeProviderService : ProviderService, Loggable {

    override fun fetchMemesFromChannel(channel: ChannelDTO): Flux<MemeDTO> = GlobalScope.flux(Unconfined) {
        val webPage = getWebPage("$NINEGAG_URL/${channel.uri}")

        val latestPostsIds = webPage
                .getElementById("jsid-latest-entries")
                .text()
                .split(",")

        latestPostsIds
                .coMap {
                    val gagDocument = getWebPage("$NINEGAG_URL/gag/$it")
                    val gagInfoJson = gagDocument.getElementsByAttributeValueContaining("type", "application/ld+json").first().html()
                    val gagInfoMap = OBJECT_MAPPER.readValue<Map<String, String>>(gagInfoJson)

                    MemeDTO(
                            id = "${channel.id}$ID_DELIMITER$it",
                            caption = fixCaption(gagInfoMap["headline"]),
                            publishedAt = parsePublishedDate(gagInfoMap),
                            attachments = listOf(AttachmentDTO(
                                    type = IMAGE,
                                    url = gagInfoMap["image"],
                                    aspectRatio = coGetImageAspectRatio(gagInfoMap["image"])
                            ))

                    )
                }
                .forEach { send(it) }
    }

    override fun getLogoUrlByChannel(channel: ChannelDTO): Mono<String> = GlobalScope.mono(Unconfined) {
        val webPage = getWebPage("$NINEGAG_URL/${channel.uri}")

        webPage.head()
                .getElementsByAttributeValueContaining("rel", "image_src")
                ?.first()
                ?.attr("href")
                ?: EMPTY
    }

    override fun sourceType(): SourceType = NINEGAG


    private fun parsePublishedDate(gagInfoMap: Map<String, String>): Date {
        return dateFrom(zonedDateTimeParse(gagInfoMap["datePublished"]).toInstant())
    }

    private fun fixCaption(caption: String?): String {
        return caption?.replace(" - 9GAG", "") ?: EMPTY
    }
}
