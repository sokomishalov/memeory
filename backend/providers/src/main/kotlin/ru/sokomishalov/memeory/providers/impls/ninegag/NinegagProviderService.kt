package ru.sokomishalov.memeory.providers.impls.ninegag

import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Service
import ru.sokomishalov.commons.core.consts.EMPTY
import ru.sokomishalov.commons.core.log.Loggable
import ru.sokomishalov.commons.core.serialization.OBJECT_MAPPER
import ru.sokomishalov.memeory.core.dto.AttachmentDTO
import ru.sokomishalov.memeory.core.dto.ChannelDTO
import ru.sokomishalov.memeory.core.dto.MemeDTO
import ru.sokomishalov.memeory.core.enums.AttachmentType.IMAGE
import ru.sokomishalov.memeory.core.enums.Provider
import ru.sokomishalov.memeory.core.enums.Provider.NINEGAG
import ru.sokomishalov.memeory.core.util.consts.DELIMITER
import ru.sokomishalov.memeory.core.util.consts.NINEGAG_URL
import ru.sokomishalov.memeory.core.util.image.getImageAspectRatio
import ru.sokomishalov.memeory.providers.ProviderService
import ru.sokomishalov.memeory.providers.util.html.getWebPage
import java.util.*
import java.time.ZonedDateTime.parse as zonedDateTimeParse
import java.util.Date.from as dateFrom


/**
 * @author sokomishalov
 */
@Service
class NinegagProviderService : ProviderService, Loggable {

    override suspend fun fetchMemes(channel: ChannelDTO, limit: Int): List<MemeDTO> {
        val webPage = getWebPage("$NINEGAG_URL/${channel.uri}")

        val latestPostsIds = webPage
                .getElementById("jsid-latest-entries")
                .text()
                .split(",")
                .take(limit)

        return latestPostsIds
                .map {
                    val gagDocument = getWebPage("$NINEGAG_URL/gag/$it")
                    val gagInfoJson = gagDocument.getElementsByAttributeValueContaining("type", "application/ld+json").first().html()
                    val gagInfoMap = OBJECT_MAPPER.readValue<Map<String, String>>(gagInfoJson)

                    MemeDTO(
                            id = "${channel.id}$DELIMITER$it",
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

    override suspend fun getLogoUrl(channel: ChannelDTO): String? {
        return getWebPage("$NINEGAG_URL/${channel.uri}")
                .head()
                .getElementsByAttributeValueContaining("rel", "image_src")
                ?.first()
                ?.attr("href")
    }

    override val provider: Provider = NINEGAG

    private fun parsePublishedDate(gagInfoMap: Map<String, String>): Date {
        return dateFrom(zonedDateTimeParse(gagInfoMap["datePublished"]).toInstant())
    }

    private fun fixCaption(caption: String?): String = caption?.replace(" - 9GAG", EMPTY) ?: EMPTY
}
