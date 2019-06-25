package ru.sokomishalov.memeory.service.api.vk

import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.ServiceActor
import com.vk.api.sdk.objects.wall.WallpostAttachmentType.*
import com.vk.api.sdk.objects.wall.WallpostAttachmentType.VIDEO
import org.springframework.context.annotation.Conditional
import org.springframework.core.io.ByteArrayResource
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
import ru.sokomishalov.memeory.enums.AttachmentType.NONE
import ru.sokomishalov.memeory.enums.SourceType
import ru.sokomishalov.memeory.enums.SourceType.VK
import ru.sokomishalov.memeory.service.api.ApiService
import ru.sokomishalov.memeory.util.EMPTY
import ru.sokomishalov.memeory.util.ID_DELIMITER
import java.util.*
import ru.sokomishalov.memeory.enums.AttachmentType.IMAGE as IMAGE_ATTACHMENT
import ru.sokomishalov.memeory.enums.AttachmentType.VIDEO as VIDEO_ATTACHMENT


/**
 * @author sokomishalov
 */
@Service
@Conditional(VkCondition::class)
class VkApiService(
        private var vkApiClient: VkApiClient,
        private val vkServiceActor: ServiceActor,
        private val props: MemeoryProperties,
        private val webClient: WebClient
) : ApiService {

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
                            attachments = it?.attachments?.map { attachment ->
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
                    )
                }
    }

    override fun getLogoByChannel(channel: ChannelDTO): Mono<ByteArray> {
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
                .flatMap { webClient.get().uri(it).exchange() }
                .flatMap { it.bodyToMono(ByteArrayResource::class.java) }
                .map { it.byteArray }
    }

    override fun sourceType(): SourceType = VK
}
