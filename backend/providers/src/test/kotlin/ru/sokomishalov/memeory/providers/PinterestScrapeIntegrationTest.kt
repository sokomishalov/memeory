package ru.sokomishalov.memeory.providers

import ru.sokomishalov.memeory.core.dto.ChannelDTO
import ru.sokomishalov.memeory.core.enums.Provider.PINTEREST

/**
 * @author sokomishalov
 */
class PinterestScrapeIntegrationTest : AbstractProviderIntegrationTest() {
    override val channel: ChannelDTO = ChannelDTO(
            id = "pinterest:celiatoler:pinterest-memes",
            name = "some funny memes (pinterest)",
            provider = PINTEREST,
            uri = "celiatoler/pinterest-memes"
    )
}