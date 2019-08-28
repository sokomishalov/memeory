package ru.sokomishalov.memeory.service.provider.twitter.api

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.withContext
import org.springframework.context.annotation.Conditional
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.sokomishalov.memeory.config.MemeoryProperties
import ru.sokomishalov.memeory.dto.AttachmentDTO
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.enums.SourceType
import ru.sokomishalov.memeory.enums.SourceType.TWITTER
import ru.sokomishalov.memeory.service.provider.ProviderService
import ru.sokomishalov.memeory.service.provider.twitter.TwitterCondition
import ru.sokomishalov.memeory.service.provider.twitter.api.TwitterApiAttachmentType.*
import ru.sokomishalov.memeory.util.consts.ID_DELIMITER
import ru.sokomishalov.memeory.util.extensions.aForEach
import ru.sokomishalov.memeory.util.extensions.aMap
import ru.sokomishalov.memeory.util.extensions.flux
import ru.sokomishalov.memeory.util.extensions.mono
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
                                private val twitter: Twitter
) : ProviderService {

    override fun fetchMemesFromChannel(channel: ChannelDTO): Flux<MemeDTO> = flux(Unconfined) {
        val userTimeLine = withContext(IO) {
            twitter.getUserTimeline(channel.uri, Paging(1, props.fetchCount))
        }

        val memes = userTimeLine.aMap {
            MemeDTO(
                    id = "${channel.id}$ID_DELIMITER${it.id}",
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

        memes.aForEach { send(it) }
    }

    override fun getLogoUrlByChannel(channel: ChannelDTO): Mono<String> = mono(Unconfined) {
        val users = withContext(IO) {
            twitter.lookupUsers(channel.uri)
        }
        users.first().biggerProfileImageURL
    }


    override fun sourceType(): SourceType = TWITTER
}
