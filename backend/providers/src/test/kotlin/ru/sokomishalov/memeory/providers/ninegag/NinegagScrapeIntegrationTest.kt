package ru.sokomishalov.memeory.providers.ninegag

import ru.sokomishalov.memeory.core.dto.ChannelDTO
import ru.sokomishalov.memeory.core.enums.Provider.NINEGAG
import ru.sokomishalov.memeory.providers.AbstractProviderIntegrationTest
import ru.sokomishalov.memeory.providers.ProviderService
import ru.sokomishalov.memeory.providers.impls.ninegag.NinegagProviderService

/**
 * @author sokomishalov
 */
class NinegagScrapeIntegrationTest : AbstractProviderIntegrationTest() {
    override val service: ProviderService = NinegagProviderService()
    override val channel: ChannelDTO = ChannelDTO(
            id = "9gag:meme",
            name = "memes (9gag)",
            provider = NINEGAG,
            uri = "meme"
    )
}