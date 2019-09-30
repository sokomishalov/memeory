package ru.sokomishalov.memeory.service.provider.vk.api

import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.ServiceActor
import com.vk.api.sdk.objects.wall.WallPostFull
import com.vk.api.sdk.objects.wall.WallpostAttachmentType.*
import com.vk.api.sdk.objects.wall.WallpostAttachmentType.VIDEO
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import org.springframework.context.annotation.Conditional
import org.springframework.stereotype.Service
import ru.sokomishalov.commons.core.collections.aMap
import ru.sokomishalov.commons.core.consts.EMPTY
import ru.sokomishalov.memeory.autoconfigure.MemeoryProperties
import ru.sokomishalov.memeory.dto.AttachmentDTO
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.enums.AttachmentType.NONE
import ru.sokomishalov.memeory.enums.SourceType
import ru.sokomishalov.memeory.enums.SourceType.VK
import ru.sokomishalov.memeory.service.provider.ProviderService
import ru.sokomishalov.memeory.service.provider.vk.VkCondition
import ru.sokomishalov.memeory.util.consts.DELIMITER
import java.util.*
import ru.sokomishalov.memeory.enums.AttachmentType.IMAGE as IMAGE_ATTACHMENT
import ru.sokomishalov.memeory.enums.AttachmentType.VIDEO as VIDEO_ATTACHMENT


/**
 * @author sokomishalov
 */
@Service
@Conditional(VkCondition::class, VkApiCondition::class)
class VkApiProviderService(
        private var vkApiClient: VkApiClient,
        private val vkServiceActor: ServiceActor,
        private val props: MemeoryProperties
) : ProviderService {

    override suspend fun fetchMemesFromChannel(channel: ChannelDTO): List<MemeDTO> {
        val response = withContext(IO) {
            vkApiClient
                    .wall()
                    .get(vkServiceActor)
                    .domain(channel.uri)
                    .count(props.fetchMaxCount)
                    .execute()
        }

        val posts = response.items

        return posts.aMap {
            MemeDTO(
                    id = "${channel.id}$DELIMITER${it.id}",
                    caption = it.text,
                    publishedAt = Date(it.date.toLong().times(1000)),
                    attachments = getAttachmentsByWallPost(it)
            )
        }
    }

    override suspend fun getLogoUrlByChannel(channel: ChannelDTO): String {
        val response = withContext(IO) {
            vkApiClient
                    .wall()
                    .getExtended(vkServiceActor)
                    .domain(channel.uri)
                    .count(1)
                    .execute()
        }

        val groupFull = response.groups.firstOrNull()

        return groupFull?.photo100 ?: groupFull?.photo50 ?: groupFull?.photo200 ?: EMPTY
    }

    override fun sourceType(): SourceType = VK


    private fun getAttachmentsByWallPost(post: WallPostFull?): List<AttachmentDTO> {
        return post
                ?.attachments
                ?.filter { it.type in listOf(PHOTO, POSTED_PHOTO, PHOTOS_LIST, VIDEO) }
                ?.map { attachment ->
                    AttachmentDTO(
                            url = attachment.photo?.let { p ->
                                p.photo807 ?: p.photo604 ?: p.photo1280 ?: p.photo130
                            },
                            type = attachment.type.let { t ->
                                when (t) {
                                    PHOTO,
                                    POSTED_PHOTO,
                                    PHOTOS_LIST -> IMAGE_ATTACHMENT
                                    VIDEO -> VIDEO_ATTACHMENT
                                    else -> NONE
                                }
                            },
                            aspectRatio = attachment.photo?.run {
                                width.toDouble().div(height.toDouble())
                            }
                    )
                }
                ?: emptyList()
    }
}
