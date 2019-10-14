package ru.sokomishalov.memeory.service.provider

import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.enums.Provider.NINEGAG

/**
 * @author sokomishalov
 */
class NinegagScrapeIntegrationTest : AbstractProviderIntegrationTest() {
    override val channel: ChannelDTO = ChannelDTO(
            id = "9gag:meme",
            name = "memes (9gag)",
            enabled = true,
            provider = NINEGAG,
            uri = "meme"
    )
}