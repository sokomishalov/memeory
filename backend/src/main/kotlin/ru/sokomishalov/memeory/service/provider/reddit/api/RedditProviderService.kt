package ru.sokomishalov.memeory.service.provider.reddit.api

import kotlinx.coroutines.Dispatchers.Unconfined
import org.springframework.context.annotation.Conditional
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.sokomishalov.memeory.config.MemeoryProperties
import ru.sokomishalov.memeory.dto.AttachmentDTO
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.enums.AttachmentType.*
import ru.sokomishalov.memeory.enums.SourceType
import ru.sokomishalov.memeory.enums.SourceType.REDDIT
import ru.sokomishalov.memeory.service.provider.ProviderService
import ru.sokomishalov.memeory.service.provider.reddit.RedditCondition
import ru.sokomishalov.memeory.service.provider.reddit.api.model.About
import ru.sokomishalov.memeory.service.provider.reddit.api.model.Listing
import ru.sokomishalov.memeory.util.consts.EMPTY
import ru.sokomishalov.memeory.util.consts.ID_DELIMITER
import ru.sokomishalov.memeory.util.consts.REDDIT_BASE_URl
import ru.sokomishalov.memeory.util.extensions.*
import java.lang.System.currentTimeMillis
import java.util.*
import java.util.UUID.randomUUID

@Service
@Conditional(RedditCondition::class)
class RedditProviderService(private val globalProps: MemeoryProperties,
                            private val webClient: WebClient
) : ProviderService {

    override fun fetchMemesFromChannel(channel: ChannelDTO): Flux<MemeDTO> = flux(Unconfined) {
        val posts = webClient
                .get()
                .uri("$REDDIT_BASE_URl/r/${channel.uri}/hot.json?limit=${globalProps.fetchCount}")
                .exchange()
                .awaitStrict()
                .awaitBody<Listing>()
                .data
                ?.children
                ?: emptyList()

        val memes = posts
                .map { it.data }
                .aMap {
                    MemeDTO(
                            id = "${channel.id}$ID_DELIMITER${it?.id ?: randomUUID()}",
                            caption = it?.title,
                            publishedAt = Date(it?.createdUtc?.toLong()?.times(1000) ?: currentTimeMillis()),
                            attachments = listOf(AttachmentDTO(
                                    url = it?.url,
                                    type = when {
                                        it?.media != null -> VIDEO
                                        it?.url != null -> IMAGE
                                        else -> NONE
                                    },
                                    aspectRatio = it?.preview?.images?.get(0)?.source?.run {
                                        width?.toDouble()?.div(height?.toDouble() ?: 1.0)
                                    }
                            ))
                    )
                }

        memes.aForEach { send(it) }
    }

    override fun getLogoUrlByChannel(channel: ChannelDTO): Mono<String> = mono(Unconfined) {
        val aboutData = webClient
                .get()
                .uri("$REDDIT_BASE_URl/r/${channel.uri}/about.json")
                .exchange()
                .awaitStrict()
                .awaitBody<About>()
                .data

        aboutData?.communityIcon?.ifBlank { aboutData.iconImg } ?: EMPTY
    }

    override fun sourceType(): SourceType = REDDIT
}
