package ru.sokomishalov.memeory.service.api.reddit

import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import ru.sokomishalov.memeory.dto.AttachmentDTO
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.enums.SourceType
import ru.sokomishalov.memeory.enums.SourceType.REDDIT
import ru.sokomishalov.memeory.service.api.ApiService
import ru.sokomishalov.memeory.service.api.reddit.model.Listing
import ru.sokomishalov.memeory.util.EMPTY

@Service
class RedditService : ApiService {

    private val client: WebClient

    init {
        this.client = WebClient
                .builder()
                .baseUrl(sourceType().baseUrl)
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .build()
    }


    override fun fetchMemesFromChannels(vararg channels: ChannelDTO): Flux<MemeDTO> {
        return Flux
                .fromArray(channels)
                .filter { it.enabled ?: false }
                .flatMap { client.get().uri("${it.uri}.json").exchange() }
                .flatMap { it.bodyToMono(Listing::class.java) }
                .map { it?.data }
                .flatMap { Flux.fromIterable(it!!.children) }
                .map { it?.data }
                .map {
                    MemeDTO(
                            id = "${sourceType()}:${it?.id}",
                            caption = it?.title,
                            attachments = listOf(AttachmentDTO(
                                    url = it?.url ?: EMPTY
                            ))
                    )
                }
    }

    final override fun sourceType(): SourceType = REDDIT

}