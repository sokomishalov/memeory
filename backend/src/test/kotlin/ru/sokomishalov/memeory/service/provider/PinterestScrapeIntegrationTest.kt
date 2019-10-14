package ru.sokomishalov.memeory.service.provider

import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.enums.Provider.PINTEREST

/**
 * @author sokomishalov
 */
class PinterestScrapeIntegrationTest : AbstractProviderIntegrationTest() {
    override val channel: ChannelDTO = ChannelDTO(
            id = "pinterest:celiatoler:pinterest-memes",
            name = "some funny memes (pinterest)",
            enabled = true,
            provider = PINTEREST,
            uri = "celiatoler/pinterest-memes"
    )
}