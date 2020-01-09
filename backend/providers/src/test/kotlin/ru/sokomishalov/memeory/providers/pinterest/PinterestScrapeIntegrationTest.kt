package ru.sokomishalov.memeory.providers.pinterest

import ru.sokomishalov.memeory.core.dto.ChannelDTO
import ru.sokomishalov.memeory.core.enums.Provider.PINTEREST
import ru.sokomishalov.memeory.providers.AbstractProviderIntegrationTest
import ru.sokomishalov.memeory.providers.ProviderService
import ru.sokomishalov.memeory.providers.impls.pinterest.PinterestProviderService

/**
 * @author sokomishalov
 */
class PinterestScrapeIntegrationTest : AbstractProviderIntegrationTest() {
    override val service: ProviderService = PinterestProviderService()
    override val channel: ChannelDTO = ChannelDTO(
            id = "pinterest:celiatoler:pinterest-memes",
            name = "some funny memes (pinterest)",
            provider = PINTEREST,
            uri = "celiatoler/pinterest-memes"
    )
}