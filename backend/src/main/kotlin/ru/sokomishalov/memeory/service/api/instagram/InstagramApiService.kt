package ru.sokomishalov.memeory.service.api.instagram

import me.postaddict.instagram.scraper.Instagram
import org.springframework.context.annotation.Conditional
import org.springframework.core.io.ByteArrayResource
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Flux.fromIterable
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import ru.sokomishalov.memeory.dto.AttachmentDTO
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.enums.AttachmentType.IMAGE
import ru.sokomishalov.memeory.enums.AttachmentType.VIDEO
import ru.sokomishalov.memeory.enums.SourceType
import ru.sokomishalov.memeory.enums.SourceType.INSTAGRAM
import ru.sokomishalov.memeory.service.api.ApiService
import ru.sokomishalov.memeory.util.ID_DELIMITER


/**
 * @author sokomishalov
 */
@Service
@Conditional(InstagramCondition::class)
class InstagramApiService(private val instagram: Instagram,
                          private val webClient: WebClient) : ApiService {

    override fun fetchMemesFromChannel(channel: ChannelDTO): Flux<MemeDTO> {
        return just(channel)
                .map { instagram.getMedias(it.uri, 1) }
                .flatMapMany { fromIterable(it.nodes) }
                .map {
                    MemeDTO(
                            id = "${channel.id}$ID_DELIMITER${it.id}",
                            caption = it.caption,
                            publishedAt = it.lastUpdated,
                            attachments = listOf(
                                    AttachmentDTO(
                                            url = it.videoUrl ?: it.displayUrl,
                                            type = when {
                                                it.isVideo -> VIDEO
                                                else -> IMAGE
                                            }
                                    )
                            )

                    )
                }
    }

    override fun getLogoByChannel(channel: ChannelDTO): Mono<ByteArray> {
        return just(channel)
                .map { instagram.getAccountByUsername(it.uri) }
                .map { it.profilePicUrl }
                .flatMap { webClient.get().uri(it).exchange() }
                .flatMap { it.bodyToMono(ByteArrayResource::class.java) }
                .map { it.byteArray }
    }

    override fun sourceType(): SourceType = INSTAGRAM
}
