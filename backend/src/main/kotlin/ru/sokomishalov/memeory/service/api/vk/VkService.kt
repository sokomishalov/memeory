package ru.sokomishalov.memeory.service.api.vk

import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.ServiceActor
import com.vk.api.sdk.objects.photos.ImageType.X
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Flux.fromIterable
import reactor.core.publisher.Mono.just
import reactor.core.scheduler.Schedulers.elastic
import ru.sokomishalov.memeory.config.props.MemeoryProperties
import ru.sokomishalov.memeory.dto.AttachmentDTO
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.enums.SourceType
import ru.sokomishalov.memeory.enums.SourceType.VK
import ru.sokomishalov.memeory.service.api.ApiService
import ru.sokomishalov.memeory.util.EMPTY
import ru.sokomishalov.memeory.util.ID_DELIMITER
import java.util.*


/**
 * @author sokomishalov
 */
@Service
class VkService(
        private var vkApiClient: VkApiClient,
        private val vkServiceActor: ServiceActor,
        private val props: MemeoryProperties
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
                            channel = channel.name,
                            caption = it.text,
                            publishedAt = Date(it.date.toLong()),
                            attachments = it?.attachments?.map { a ->
                                AttachmentDTO(
                                        url = a?.photo?.images?.find { img -> img.type == X }?.url?.toString() ?: EMPTY
                                )
                            }
                    )
                }
                .subscribeOn(elastic())
    }

    override fun sourceType(): SourceType = VK
}
