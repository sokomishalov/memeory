package ru.sokomishalov.memeory.service.provider.ninegag.scrape

import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.context.annotation.Conditional
import org.springframework.stereotype.Service
import ru.sokomishalov.commons.core.collections.aMap
import ru.sokomishalov.commons.core.consts.EMPTY
import ru.sokomishalov.commons.core.html.getWebPage
import ru.sokomishalov.commons.core.images.getImageAspectRatio
import ru.sokomishalov.commons.core.log.Loggable
import ru.sokomishalov.commons.core.serialization.OBJECT_MAPPER
import ru.sokomishalov.memeory.dto.AttachmentDTO
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.enums.AttachmentType.IMAGE
import ru.sokomishalov.memeory.enums.SourceType
import ru.sokomishalov.memeory.enums.SourceType.NINEGAG
import ru.sokomishalov.memeory.service.provider.ProviderService
import ru.sokomishalov.memeory.service.provider.ninegag.NinegagCondition
import ru.sokomishalov.memeory.util.consts.ID_DELIMITER
import ru.sokomishalov.memeory.util.consts.NINEGAG_URL
import java.util.*
import java.time.ZonedDateTime.parse as zonedDateTimeParse
import java.util.Date.from as dateFrom


/**
 * @author sokomishalov
 */
@Service
@Conditional(NinegagCondition::class, NinegagScrapeCondition::class)
class NinegagScrapeProviderService : ProviderService, Loggable {

    override suspend fun fetchMemesFromChannel(channel: ChannelDTO): List<MemeDTO> {
        val webPage = getWebPage("$NINEGAG_URL/${channel.uri}")

        val latestPostsIds = webPage
                .getElementById("jsid-latest-entries")
                .text()
                .split(",")

        return latestPostsIds
                .aMap {
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
                                    aspectRatio = getImageAspectRatio(gagInfoMap["image"])
                            ))

                    )
                }
    }

    override suspend fun getLogoUrlByChannel(channel: ChannelDTO): String {
        return getWebPage("$NINEGAG_URL/${channel.uri}")
                .head()
                .getElementsByAttributeValueContaining("rel", "image_src")
                ?.first()
                ?.attr("href")
                ?: EMPTY
    }

    override fun sourceType(): SourceType = NINEGAG


    private fun parsePublishedDate(gagInfoMap: Map<String, String>): Date {
        return dateFrom(zonedDateTimeParse(gagInfoMap["datePublished"]).toInstant())
    }

    private fun fixCaption(caption: String?): String = caption?.replace(" - 9GAG", EMPTY) ?: EMPTY
}
