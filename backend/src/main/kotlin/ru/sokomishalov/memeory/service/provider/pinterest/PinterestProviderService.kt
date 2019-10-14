package ru.sokomishalov.memeory.service.provider.pinterest

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.stereotype.Service
import ru.sokomishalov.commons.core.collections.aMap
import ru.sokomishalov.commons.core.html.getWebPage
import ru.sokomishalov.commons.core.log.Loggable
import ru.sokomishalov.commons.core.serialization.OBJECT_MAPPER
import ru.sokomishalov.commons.core.serialization.aReadTree
import ru.sokomishalov.memeory.dto.AttachmentDTO
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.enums.AttachmentType.IMAGE
import ru.sokomishalov.memeory.enums.Provider
import ru.sokomishalov.memeory.enums.Provider.PINTEREST
import ru.sokomishalov.memeory.service.provider.ProviderService
import ru.sokomishalov.memeory.util.consts.DELIMITER
import ru.sokomishalov.memeory.util.consts.PINTEREST_URL
import java.util.Locale.ROOT
import java.time.ZonedDateTime.parse as zonedDateTimeParse
import java.time.format.DateTimeFormatter.ofPattern as dateTimeFormatterOfPattern
import java.util.Date.from as dateFrom


/**
 * @author sokomishalov
 */
@Service
class PinterestProviderService : ProviderService, Loggable {

    companion object {
        private val DATE_FORMATTER = dateTimeFormatterOfPattern("EEE, d MMM yyyy HH:mm:ss Z", ROOT)
    }

    override suspend fun fetchMemes(channel: ChannelDTO): List<MemeDTO> {
        val infoJsonNode = parseInitJson(channel)
        val feedList = infoJsonNode["resourceDataCache"][1]["data"]["board_feed"].asIterable()

        return feedList
                .aMap {
                    val imageInfo = it["images"]["orig"]
                    MemeDTO(
                            id = "${channel.id}$DELIMITER${it["id"].asText()}",
                            caption = it["description"]?.asText(),
                            publishedAt = dateFrom(zonedDateTimeParse(it["created_at"]?.asText(), DATE_FORMATTER).toInstant()),
                            attachments = listOf(AttachmentDTO(
                                    type = IMAGE,
                                    url = imageInfo["url"]?.asText(),
                                    aspectRatio = imageInfo["width"].asDouble() / imageInfo["height"].asDouble()
                            ))
                    )
                }
    }

    override suspend fun getLogoUrl(channel: ChannelDTO): String? {
        val infoJsonNode = parseInitJson(channel)

        return infoJsonNode["resourceDataCache"]
                ?.first()
                ?.get("data")
                ?.get("owner")
                ?.get("image_medium_url")
                ?.asText()
    }

    override val provider: Provider = PINTEREST

    private suspend fun parseInitJson(channel: ChannelDTO): JsonNode {
        val webPage = getWebPage("$PINTEREST_URL/${channel.uri}")
        val infoJson = webPage.getElementById("jsInit1").html()
        return OBJECT_MAPPER.aReadTree(infoJson)
    }
}
