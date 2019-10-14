package ru.sokomishalov.memeory.service.provider

import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.enums.Provider.REDDIT

/**
 * @author sokomishalov
 */
class RedditApiIntegrationTest : AbstractProviderIntegrationTest() {
    override val channel: ChannelDTO = ChannelDTO(
            id = "reddit:memes",
            name = "memes (Reddit)",
            enabled = true,
            provider = REDDIT,
            uri = "memes"
    )
}