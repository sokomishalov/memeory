package ru.sokomishalov.memeory.service.provider.facebook.graphapi

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import org.springframework.context.annotation.Conditional
import org.springframework.social.facebook.api.Facebook
import org.springframework.social.facebook.api.Post.PostType.PHOTO
import org.springframework.social.facebook.api.Post.PostType.VIDEO
import org.springframework.stereotype.Service
import ru.sokomishalov.memeory.dto.AttachmentDTO
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.enums.AttachmentType.NONE
import ru.sokomishalov.memeory.enums.SourceType
import ru.sokomishalov.memeory.enums.SourceType.FACEBOOK
import ru.sokomishalov.memeory.service.provider.ProviderService
import ru.sokomishalov.memeory.service.provider.facebook.FacebookCondition
import ru.sokomishalov.memeory.util.consts.ID_DELIMITER
import ru.sokomishalov.memeory.util.extensions.aMap
import ru.sokomishalov.memeory.enums.AttachmentType.IMAGE as IMAGE_ATTACHMENT
import ru.sokomishalov.memeory.enums.AttachmentType.VIDEO as VIDEO_ATTACHMENT


/**
 * @author sokomishalov
 */
@Service
@Conditional(FacebookCondition::class, FacebookGraphApiCondition::class)
class FacebookGraphProviderService(private val facebook: Facebook) : ProviderService {

    override suspend fun fetchMemesFromChannel(channel: ChannelDTO): List<MemeDTO> {
        val feed = withContext(IO) {
            facebook.feedOperations().getFeed(channel.uri)
        }

        return feed.aMap {
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

    override suspend fun getLogoUrlByChannel(channel: ChannelDTO): String = withContext(IO) {
        facebook.groupOperations().getGroup(channel.uri).icon
    }


    override fun sourceType(): SourceType = FACEBOOK
}
