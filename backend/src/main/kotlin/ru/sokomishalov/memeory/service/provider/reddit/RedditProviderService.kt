package ru.sokomishalov.memeory.service.provider.reddit

import org.springframework.context.annotation.Conditional
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Flux.fromIterable
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import ru.sokomishalov.memeory.config.MemeoryProperties
import ru.sokomishalov.memeory.dto.AttachmentDTO
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.enums.AttachmentType.*
import ru.sokomishalov.memeory.enums.SourceType
import ru.sokomishalov.memeory.enums.SourceType.REDDIT
import ru.sokomishalov.memeory.service.provider.ProviderService
import ru.sokomishalov.memeory.service.provider.reddit.model.About
import ru.sokomishalov.memeory.service.provider.reddit.model.Listing
import ru.sokomishalov.memeory.util.EMPTY
import ru.sokomishalov.memeory.util.ID_DELIMITER
import ru.sokomishalov.memeory.util.REDDIT_BASE_URl
import java.lang.System.currentTimeMillis
import java.util.*
import java.util.UUID.randomUUID

@Service
@Conditional(RedditCondition::class)
class RedditProviderService(private val globalProps: MemeoryProperties,
                            private val webClient: WebClient
) : ProviderService {

    override fun fetchMemesFromChannel(channel: ChannelDTO): Flux<MemeDTO> {
        return just(channel)
                .flatMap {
                    webClient
                            .get()
                            .uri("$REDDIT_BASE_URl/r/${it.uri}/hot.json?limit=${globalProps.fetchCount}")
                            .exchange()
                }
                .flatMap { it.bodyToMono(Listing::class.java) }
                .map { it?.data }
                .flatMapMany { fromIterable(it?.children ?: emptyList()) }
                .map { it?.data }
                .map {
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
    }

    override fun getLogoUrlByChannel(channel: ChannelDTO): Mono<String> {
        return just(channel)
                .flatMap {
                    webClient
                            .get()
                            .uri("$REDDIT_BASE_URl/r/${it.uri}/about.json")
                            .exchange()
                }
                .flatMap { it.bodyToMono(About::class.java) }
                .map { it?.data }
                .map { it?.communityIcon?.ifBlank { it.iconImg } ?: EMPTY }
    }

    override fun sourceType(): SourceType = REDDIT
}
