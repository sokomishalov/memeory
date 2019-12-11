package ru.sokomishalov.memeory.providers.impls.reddit

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.awaitBody
import ru.sokomishalov.commons.core.collections.aMap
import ru.sokomishalov.commons.core.consts.EMPTY
import ru.sokomishalov.commons.core.reactor.awaitStrict
import ru.sokomishalov.commons.spring.webclient.REACTIVE_WEB_CLIENT
import ru.sokomishalov.memeory.core.dto.AttachmentDTO
import ru.sokomishalov.memeory.core.dto.ChannelDTO
import ru.sokomishalov.memeory.core.dto.MemeDTO
import ru.sokomishalov.memeory.core.enums.AttachmentType.*
import ru.sokomishalov.memeory.core.enums.Provider
import ru.sokomishalov.memeory.core.enums.Provider.REDDIT
import ru.sokomishalov.memeory.core.util.consts.DELIMITER
import ru.sokomishalov.memeory.core.util.consts.REDDIT_BASE_URL
import ru.sokomishalov.memeory.providers.ProviderService
import java.lang.System.currentTimeMillis
import java.util.*

@Service
class RedditProviderService : ProviderService {

    override suspend fun fetchMemes(channel: ChannelDTO, limit: Int): List<MemeDTO> {
        val response = REACTIVE_WEB_CLIENT
                .get()
                .uri("$REDDIT_BASE_URL/r/${channel.uri}/hot.json?limit=${limit}")
                .exchange()
                .awaitStrict()
                .awaitBody<JsonNode>()

        val posts = response["data"]["children"].elementsToList()

        return posts
                .mapNotNull { it["data"] }
                .aMap {
                    MemeDTO(
                            id = "${channel.id}$DELIMITER${it.getValue("id")}",
                            caption = it.getValue("title"),
                            publishedAt = Date(it.getValue("created_utc")?.toBigDecimal()?.longValueExact()?.times(1000)
                                    ?: currentTimeMillis()),
                            attachments = listOf(AttachmentDTO(
                                    url = it.getValue("url"),
                                    type = when {
                                        it["media"].isEmpty.not() -> VIDEO
                                        it.getValue("url") != null -> IMAGE
                                        else -> NONE
                                    },
                                    aspectRatio = it.get("preview")?.get("images")?.elementsToList()?.firstOrNull()?.get("source")?.run {
                                        getValue("width")?.toDouble()?.div(getValue("height")?.toDouble() ?: 1.0)
                                    }
                            ))
                    )
                }
    }

    override suspend fun getLogoUrl(channel: ChannelDTO): String? {
        val response = REACTIVE_WEB_CLIENT
                .get()
                .uri("$REDDIT_BASE_URL/r/${channel.uri}/about.json")
                .exchange()
                .awaitStrict()
                .awaitBody<JsonNode>()

        val communityIcon = response["data"].getValue("community_icon")
        val imgIcon = response["data"].getValue("icon_img")

        return communityIcon?.ifBlank { imgIcon }
    }

    override val provider: Provider = REDDIT

    private fun JsonNode?.getValue(field: String): String? {
        return this
                ?.get(field)
                ?.asText()
                ?.replace("null", EMPTY)
                ?.ifBlank { null }
    }

    private fun JsonNode?.elementsToList(): List<JsonNode> {
        return this?.elements()?.asSequence()?.toList() ?: emptyList()
    }
}