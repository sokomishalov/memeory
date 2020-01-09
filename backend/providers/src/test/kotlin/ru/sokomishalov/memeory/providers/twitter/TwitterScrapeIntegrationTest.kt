package ru.sokomishalov.memeory.providers.twitter

import ru.sokomishalov.memeory.core.dto.ChannelDTO
import ru.sokomishalov.memeory.core.enums.Provider.TWITTER
import ru.sokomishalov.memeory.providers.AbstractProviderIntegrationTest
import ru.sokomishalov.memeory.providers.ProviderService
import ru.sokomishalov.memeory.providers.impls.twitter.TwitterProviderService

/**
 * @author sokomishalov
 */
class TwitterScrapeIntegrationTest : AbstractProviderIntegrationTest() {
    override val service: ProviderService = TwitterProviderService()
    override val channel: ChannelDTO = ChannelDTO(
            id = "twitter:russianmemesltd",
            name = "Russian Memes United (Twitter)",
            provider = TWITTER,
            uri = "russianmemesltd"
    )
}