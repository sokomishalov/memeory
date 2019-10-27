package ru.sokomishalov.memeory.providers

import ru.sokomishalov.memeory.core.dto.ChannelDTO
import ru.sokomishalov.memeory.core.enums.Provider.NINEGAG

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