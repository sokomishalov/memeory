package ru.sokomishalov.memeory.service.api.reddit

import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Flux.fromIterable
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import ru.sokomishalov.memeory.config.props.MemeoryProperties
import ru.sokomishalov.memeory.dto.AttachmentDTO
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.enums.AttachmentType.*
import ru.sokomishalov.memeory.enums.SourceType
import ru.sokomishalov.memeory.enums.SourceType.REDDIT
import ru.sokomishalov.memeory.service.api.ApiService
import ru.sokomishalov.memeory.service.api.reddit.model.About
import ru.sokomishalov.memeory.service.api.reddit.model.Listing
import ru.sokomishalov.memeory.util.EMPTY
import ru.sokomishalov.memeory.util.ID_DELIMITER
import java.lang.System.currentTimeMillis
import java.util.*
import java.util.UUID.randomUUID

@Service
class RedditService(props: RedditConfigurationProperties,
                    private val globalProps: MemeoryProperties) : ApiService {

    private val client: WebClient = WebClient
            .builder()
            .baseUrl(props.baseUrl)
            .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
            .build()


    override fun fetchMemesFromChannel(channel: ChannelDTO): Flux<MemeDTO> {
        return just(channel)
                .flatMap {
                    client
                            .get()
                            .uri("/r/${it.uri}/hot.json?limit=${globalProps.fetchCount}")
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
                                    }
                            ))
                    )
                }
    }

    override fun getLogoByChannel(channel: ChannelDTO): Mono<ByteArray> {
        return just(channel)
                .flatMap {
                    client
                            .get()
                            .uri("/r/${it.uri}/about.json")
                            .exchange()
                }
                .flatMap { it.bodyToMono(About::class.java) }
                .map { it?.data }
                .map { it?.communityIcon?.ifBlank { it.iconImg } ?: EMPTY }
                .flatMap { client.get().uri(it).exchange() }
                .flatMap { it.bodyToMono(ByteArrayResource::class.java) }
                .map { it.byteArray }

    }

    override fun sourceType(): SourceType = REDDIT
}
