package ru.sokomishalov.memeory.service.provider.instagram.scrape

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.withContext
import me.postaddict.instagram.scraper.Instagram
import org.springframework.context.annotation.Conditional
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
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
import ru.sokomishalov.memeory.util.extensions.aForEach
import ru.sokomishalov.memeory.util.extensions.aMap
import ru.sokomishalov.memeory.util.extensions.flux
import ru.sokomishalov.memeory.util.extensions.mono


/**
 * @author sokomishalov
 */
@Service
@Conditional(InstagramCondition::class, InstagramScrapeCondition::class)
class InstagramScrapeProviderService(private val instagram: Instagram) : ProviderService {

    override fun fetchMemesFromChannel(channel: ChannelDTO): Flux<MemeDTO> = flux(Unconfined) {
        val posts = withContext(IO) {
            instagram.getMedias(channel.uri, 1).nodes
        }

        val memes = posts.aMap {
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

        memes.aForEach { send(it) }
    }

    override fun getLogoUrlByChannel(channel: ChannelDTO): Mono<String> = mono(IO) {
        instagram.getAccountByUsername(channel.uri).profilePicUrl
    }

    override fun sourceType(): SourceType = INSTAGRAM
}
