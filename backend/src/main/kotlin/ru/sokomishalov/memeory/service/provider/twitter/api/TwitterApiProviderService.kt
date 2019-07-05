package ru.sokomishalov.memeory.service.provider.twitter.api

import org.springframework.context.annotation.Conditional
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
import ru.sokomishalov.memeory.enums.SourceType
import ru.sokomishalov.memeory.enums.SourceType.TWITTER
import ru.sokomishalov.memeory.service.provider.ProviderService
import ru.sokomishalov.memeory.service.provider.twitter.TwitterCondition
import ru.sokomishalov.memeory.service.provider.twitter.api.TwitterApiAttachmentType.*
import ru.sokomishalov.memeory.util.getImageByteArrayMonoByUrl
import twitter4j.Paging
import twitter4j.Twitter
import ru.sokomishalov.memeory.enums.AttachmentType.IMAGE as IMAGE_ATTACHMENT
import ru.sokomishalov.memeory.enums.AttachmentType.VIDEO as VIDEO_ATTACHMENT


/**
 * @author sokomishalov
 */
@Service
@Conditional(TwitterCondition::class, TwitterApiCondition::class)
class TwitterApiProviderService(private val props: MemeoryProperties,
                                private val twitter: Twitter,
                                private val webClient: WebClient
) : ProviderService {
    override fun fetchMemesFromChannel(channel: ChannelDTO): Flux<MemeDTO> {
        return just(channel)
                .map { twitter.getUserTimeline(it.uri, Paging(1, props.fetchCount)) }
                .flatMapMany { fromIterable(it) }
                .map {
                    MemeDTO(
                            id = it.id.toString(),
                            caption = it.text,
                            publishedAt = it.createdAt,
                            attachments = it.mediaEntities.map { me ->
                                AttachmentDTO(
                                        url = me.mediaURL,
                                        type = when (valueOf(me.type.toUpperCase())) {
                                            PHOTO,
                                            ANIMATED_GIF -> IMAGE_ATTACHMENT
                                            VIDEO -> VIDEO_ATTACHMENT
                                        },
                                        aspectRatio = me.sizes.entries.first().value.run { width.toDouble() / height }

                                )
                            }

                    )
                }
    }

    override fun getLogoByChannel(channel: ChannelDTO): Mono<ByteArray> {
        return just(channel)
                .map { twitter.lookupUsers(it.uri) }
                .map { it.first() }
                .map { it.biggerProfileImageURL }
                .flatMap { getImageByteArrayMonoByUrl(it, webClient) }
    }

    override fun sourceType(): SourceType = TWITTER
}