package ru.sokomishalov.memeory.service.provider.reddit.api

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.context.annotation.Conditional
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import ru.sokomishalov.commons.core.collections.aMap
import ru.sokomishalov.commons.core.consts.EMPTY
import ru.sokomishalov.commons.core.reactor.awaitStrict
import ru.sokomishalov.memeory.autoconfigure.MemeoryProperties
import ru.sokomishalov.memeory.dto.AttachmentDTO
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.enums.AttachmentType.*
import ru.sokomishalov.memeory.enums.SourceType
import ru.sokomishalov.memeory.enums.SourceType.REDDIT
import ru.sokomishalov.memeory.service.provider.ProviderService
import ru.sokomishalov.memeory.service.provider.reddit.RedditCondition
import ru.sokomishalov.memeory.util.consts.DELIMITER
import ru.sokomishalov.memeory.util.consts.REDDIT_BASE_URL
import java.lang.System.currentTimeMillis
import java.util.*

@Service
@Conditional(RedditCondition::class)
class RedditProviderService(private val globalProps: MemeoryProperties,
                            private val webClient: WebClient
) : ProviderService {

    override suspend fun fetchMemesFromChannel(channel: ChannelDTO): List<MemeDTO> {
        val response = webClient
                .get()
                .uri("$REDDIT_BASE_URL/r/${channel.uri}/hot.json?limit=${globalProps.fetchMaxCount}")
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
                                    aspectRatio = it["preview"]["images"].elementsToList().firstOrNull()?.get("source")?.run {
                                        getValue("width")?.toDouble()?.div(getValue("height")?.toDouble() ?: 1.0)
                                    }
                            ))
                    )
                }
    }

    override suspend fun getLogoUrlByChannel(channel: ChannelDTO): String {
        val response = webClient
                .get()
                .uri("$REDDIT_BASE_URL/r/${channel.uri}/about.json")
                .exchange()
                .awaitStrict()
                .awaitBody<JsonNode>()

        val communityIcon = response["data"].getValue("community_icon")
        val imgIcon = response["data"].getValue("icon_img")

        return communityIcon?.ifBlank { imgIcon }.orEmpty()
    }

    override fun sourceType(): SourceType = REDDIT

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