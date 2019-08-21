package ru.sokomishalov.memeory.service.provider.vk

import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.ServiceActor
import com.vk.api.sdk.objects.wall.WallPostFull
import com.vk.api.sdk.objects.wall.WallpostAttachmentType.*
import com.vk.api.sdk.objects.wall.WallpostAttachmentType.VIDEO
import org.springframework.context.annotation.Conditional
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Flux.fromIterable
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import ru.sokomishalov.memeory.config.MemeoryProperties
import ru.sokomishalov.memeory.dto.AttachmentDTO
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.enums.AttachmentType.NONE
import ru.sokomishalov.memeory.enums.SourceType
import ru.sokomishalov.memeory.enums.SourceType.VK
import ru.sokomishalov.memeory.service.provider.ProviderService
import ru.sokomishalov.memeory.util.consts.EMPTY
import ru.sokomishalov.memeory.util.consts.ID_DELIMITER
import java.util.*
import ru.sokomishalov.memeory.enums.AttachmentType.IMAGE as IMAGE_ATTACHMENT
import ru.sokomishalov.memeory.enums.AttachmentType.VIDEO as VIDEO_ATTACHMENT


/**
 * @author sokomishalov
 */
@Service
@Conditional(VkCondition::class)
class VkProviderService(
        private var vkApiClient: VkApiClient,
        private val vkServiceActor: ServiceActor,
        private val props: MemeoryProperties
) : ProviderService {

    override fun fetchMemesFromChannel(channel: ChannelDTO): Flux<MemeDTO> {
        return just(channel)
                .map {
                    vkApiClient
                            .wall()
                            .get(vkServiceActor)
                            .domain(it.uri)
                            .count(props.fetchCount)
                            .execute()
                }
                .map { it.items }
                .flatMapMany { fromIterable(it) }
                .map {
                    MemeDTO(
                            id = "${channel.id}$ID_DELIMITER${it.id}",
                            caption = it.text,
                            publishedAt = Date(it.date.toLong().times(1000)),
                            attachments = getAttachmentsByWallPost(it)
                    )
                }
    }

    private fun getAttachmentsByWallPost(post: WallPostFull?): List<AttachmentDTO> {
        return post
                ?.attachments
                ?.map { attachment ->
                    AttachmentDTO(
                            url = attachment?.photo?.let { p ->
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
                            aspectRatio = attachment?.photo?.run {
                                width.toDouble().div(height.toDouble())
                            }
                    )
                }
                ?: emptyList()
    }

    override fun getLogoUrlByChannel(channel: ChannelDTO): Mono<String> {
        return just(channel)
                .map {
                    vkApiClient
                            .wall()
                            .getExtended(vkServiceActor)
                            .domain(it.uri)
                            .count(1)
                            .execute()
                }
                .map { it.groups }
                .flatMapMany { fromIterable(it) }
                .map { it?.photo100 ?: it.photo50 ?: it?.photo200 ?: EMPTY }
                .next()
    }

    override fun sourceType(): SourceType = VK
}
