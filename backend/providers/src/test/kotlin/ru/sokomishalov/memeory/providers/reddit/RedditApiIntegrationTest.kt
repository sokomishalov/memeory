package ru.sokomishalov.memeory.providers.reddit

import ru.sokomishalov.memeory.core.dto.ChannelDTO
import ru.sokomishalov.memeory.core.enums.Provider.REDDIT
import ru.sokomishalov.memeory.providers.AbstractProviderIntegrationTest
import ru.sokomishalov.memeory.providers.ProviderService
import ru.sokomishalov.memeory.providers.impls.reddit.RedditProviderService

/**
 * @author sokomishalov
 */
class RedditApiIntegrationTest : AbstractProviderIntegrationTest() {
    override val service: ProviderService = RedditProviderService()
    override val channel: ChannelDTO = ChannelDTO(
            id = "reddit:memes",
            name = "memes (Reddit)",
            provider = REDDIT,
            uri = "memes"
    )
}