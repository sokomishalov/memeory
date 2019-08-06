package ru.sokomishalov.memeory.service.provider.facebook.graphapi

import org.springframework.context.annotation.Conditional
import org.springframework.social.facebook.api.Facebook
import org.springframework.social.facebook.api.Post.PostType.PHOTO
import org.springframework.social.facebook.api.Post.PostType.VIDEO
import org.springframework.stereotype.Service
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
import ru.sokomishalov.memeory.service.provider.ProviderService
import ru.sokomishalov.memeory.service.provider.facebook.FacebookCondition
import ru.sokomishalov.memeory.util.ID_DELIMITER
import ru.sokomishalov.memeory.enums.AttachmentType.IMAGE as IMAGE_ATTACHMENT
import ru.sokomishalov.memeory.enums.AttachmentType.VIDEO as VIDEO_ATTACHMENT


/**
 * @author sokomishalov
 */
@Service
@Conditional(FacebookCondition::class, FacebookGraphApiCondition::class)
class FacebookGraphProviderService(private val facebook: Facebook) : ProviderService {

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

    override fun getLogoUrlByChannel(channel: ChannelDTO): Mono<String> {
        return just(channel)
                .map { facebook.groupOperations().getGroup(it.uri) }
                .map { it.icon }
    }

    override fun sourceType(): SourceType = FACEBOOK
}
