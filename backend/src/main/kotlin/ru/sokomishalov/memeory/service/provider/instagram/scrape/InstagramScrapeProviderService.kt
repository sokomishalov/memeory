package ru.sokomishalov.memeory.service.provider.instagram.scrape

import me.postaddict.instagram.scraper.Instagram
import org.springframework.context.annotation.Conditional
import org.springframework.stereotype.Service
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
import ru.sokomishalov.memeory.service.provider.ProviderService
import ru.sokomishalov.memeory.service.provider.instagram.InstagramCondition
import ru.sokomishalov.memeory.util.consts.ID_DELIMITER


/**
 * @author sokomishalov
 */
@Service
@Conditional(InstagramCondition::class, InstagramScrapeCondition::class)
// TODO rewrite
class InstagramScrapeProviderService(private val instagram: Instagram) : ProviderService {

    override fun fetchMemesFromChannel(channel: ChannelDTO): Flux<MemeDTO> {
        return just(channel)
                .map { instagram.getMedias(it.uri, 1) }
                .flatMapMany { fromIterable(it.nodes) }
                .map {
                    MemeDTO(
                            id = "${channel.id}$ID_DELIMITER${it.id}",
                            caption = it.caption,
                            publishedAt = it.created,
                            attachments = listOf(
                                    AttachmentDTO(
                                            url = it.videoUrl ?: it.displayUrl,
                                            type = when {
                                                it.isVideo -> VIDEO
                                                else -> IMAGE
                                            },
                                            aspectRatio = it.width.toDouble().div(it.height)
                                    )
                            )

                    )
                }
    }

    override fun getLogoUrlByChannel(channel: ChannelDTO): Mono<String> {
        return just(channel)
                .map { instagram.getAccountByUsername(it.uri) }
                .map { it.profilePicUrl }
    }

    override fun sourceType(): SourceType = INSTAGRAM
}
