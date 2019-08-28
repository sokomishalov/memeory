package ru.sokomishalov.memeory.service.provider.pinterest.scrape

import com.fasterxml.jackson.databind.JsonNode
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.springframework.context.annotation.Conditional
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.sokomishalov.memeory.dto.AttachmentDTO
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.enums.AttachmentType.IMAGE
import ru.sokomishalov.memeory.enums.SourceType
import ru.sokomishalov.memeory.enums.SourceType.PINTEREST
import ru.sokomishalov.memeory.service.provider.ProviderService
import ru.sokomishalov.memeory.service.provider.pinterest.PinterestCondition
import ru.sokomishalov.memeory.util.consts.ID_DELIMITER
import ru.sokomishalov.memeory.util.consts.PINTEREST_URL
import ru.sokomishalov.memeory.util.extensions.aMap
import ru.sokomishalov.memeory.util.extensions.aReadTree
import ru.sokomishalov.memeory.util.extensions.flux
import ru.sokomishalov.memeory.util.extensions.mono
import ru.sokomishalov.memeory.util.log.Loggable
import ru.sokomishalov.memeory.util.scrape.getWebPage
import ru.sokomishalov.memeory.util.serialization.OBJECT_MAPPER
import java.util.Locale.ROOT
import java.time.ZonedDateTime.parse as zonedDateTimeParse
import java.time.format.DateTimeFormatter.ofPattern as dateTimeFormatterOfPattern
import java.util.Date.from as dateFrom


/**
 * @author sokomishalov
 */
@Service
@Conditional(PinterestCondition::class, PinterestScrapeCondition::class)
@ExperimentalCoroutinesApi
class PinterestScrapeProviderService : ProviderService, Loggable {

    private val dateTimeFormatter = dateTimeFormatterOfPattern("EEE, d MMM yyyy HH:mm:ss Z", ROOT)

    override fun fetchMemesFromChannel(channel: ChannelDTO): Flux<MemeDTO> = flux {
        val infoJsonNode = parseInitJson(channel)
        val feedList = infoJsonNode["resourceDataCache"][1]["data"]["board_feed"].asIterable()

        feedList
                .aMap {
                    val imageInfo = it["images"]["orig"]
                    MemeDTO(
                            id = "${channel.id}$ID_DELIMITER${it["id"].asText()}",
                            caption = it["description"]?.asText(),
                            publishedAt = dateFrom(zonedDateTimeParse(it["created_at"]?.asText(), dateTimeFormatter).toInstant()),
                            attachments = listOf(AttachmentDTO(
                                    type = IMAGE,
                                    url = imageInfo["url"]?.asText(),
                                    aspectRatio = imageInfo["width"].asDouble() / imageInfo["height"].asDouble()
                            ))
                    )
                }
                .forEach { send(it) }
    }

    override fun getLogoUrlByChannel(channel: ChannelDTO): Mono<String> = mono {
        val infoJsonNode = parseInitJson(channel)

        infoJsonNode["resourceDataCache"][0]["data"]["owner"]["image_medium_url"].asText()
    }

    override fun sourceType(): SourceType = PINTEREST


    private suspend fun parseInitJson(channel: ChannelDTO): JsonNode {
        val webPage = getWebPage("$PINTEREST_URL/${channel.uri}")
        val infoJson = webPage.getElementById("jsInit1").html()
        return OBJECT_MAPPER.aReadTree(infoJson)
    }
}
