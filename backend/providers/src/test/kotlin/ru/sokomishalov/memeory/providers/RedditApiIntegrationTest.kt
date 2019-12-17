package ru.sokomishalov.memeory.providers

import ru.sokomishalov.memeory.core.dto.ChannelDTO
import ru.sokomishalov.memeory.core.enums.Provider.REDDIT

/**
 * @author sokomishalov
 */
class RedditApiIntegrationTest : AbstractProviderIntegrationTest() {
    override val channel: ChannelDTO = ChannelDTO(
            id = "reddit:memes",
            name = "memes (Reddit)",
            provider = REDDIT,
            uri = "memes"
    )
}