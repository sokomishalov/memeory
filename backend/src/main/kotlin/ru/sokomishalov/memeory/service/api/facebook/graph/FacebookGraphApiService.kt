package ru.sokomishalov.memeory.service.api.facebook.graph

import org.springframework.context.annotation.Conditional
import org.springframework.core.io.ByteArrayResource
import org.springframework.social.facebook.api.Facebook
import org.springframework.social.facebook.api.Post.PostType.PHOTO
import org.springframework.social.facebook.api.Post.PostType.VIDEO
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Flux.fromIterable
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import ru.sokomishalov.memeory.dto.AttachmentDTO
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.enums.AttachmentType.NONE
import ru.sokomishalov.memeory.enums.SourceType
import ru.sokomishalov.memeory.enums.SourceType.FACEBOOK
import ru.sokomishalov.memeory.service.api.ApiService
import ru.sokomishalov.memeory.service.api.facebook.FacebookCondition
import ru.sokomishalov.memeory.util.ID_DELIMITER
import ru.sokomishalov.memeory.enums.AttachmentType.IMAGE as IMAGE_ATTACHMENT
import ru.sokomishalov.memeory.enums.AttachmentType.VIDEO as VIDEO_ATTACHMENT


/**
 * @author sokomishalov
 */
@Service
@Conditional(FacebookCondition::class, FacebookGraphApiCondition::class)
class FacebookGraphApiService(private val facebook: Facebook,
                              private val webClient: WebClient
) : ApiService {

    override fun fetchMemesFromChannel(channel: ChannelDTO): Flux<MemeDTO> {
        return just(channel)
                .map { facebook.feedOperations().getFeed(it.uri) }
                .flatMapMany { fromIterable(it) }
                .map {
                    MemeDTO(
                            id = "${channel.id}$ID_DELIMITER${it.id}",
                            caption = it.caption,
                            publishedAt = it.createdTime,
                            attachments = listOf(
                                    AttachmentDTO(
                                            url = it.picture,
                                            type = when (it.type) {
                                                PHOTO -> IMAGE_ATTACHMENT
                                                VIDEO -> VIDEO_ATTACHMENT
                                                else -> NONE
                                            }
                                    )
                            )
                    )
                }
    }

    override fun getLogoByChannel(channel: ChannelDTO): Mono<ByteArray> {
        return just(channel)
                .map { facebook.groupOperations().getGroup(it.uri) }
                .map { it.icon }
                .flatMap { webClient.get().uri(it).exchange() }
                .flatMap { it.bodyToMono(ByteArrayResource::class.java) }
                .map { it.byteArray }
    }

    override fun sourceType(): SourceType = FACEBOOK
}
