package ru.sokomishalov.memeory.service.provider

import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.enums.SourceType.NINEGAG

/**
 * @author sokomishalov
 */
class NinegagScrapeIntegrationTest : AbstractProviderIntegrationTest() {
    override val channel: ChannelDTO = ChannelDTO(
            id = "9gag:meme",
            name = "memes (9gag)",
            enabled = true,
            sourceType = NINEGAG,
            uri = "meme"
    )
}