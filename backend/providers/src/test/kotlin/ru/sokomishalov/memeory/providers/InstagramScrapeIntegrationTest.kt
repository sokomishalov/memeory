package ru.sokomishalov.memeory.providers

import ru.sokomishalov.memeory.core.dto.ChannelDTO
import ru.sokomishalov.memeory.core.enums.Provider.INSTAGRAM

/**
 * @author sokomishalov
 */
class InstagramScrapeIntegrationTest : AbstractProviderIntegrationTest() {
    override val channel: ChannelDTO = ChannelDTO(
            id = "instagram:originaltrollfootball",
            name = "Troll Football (Instagram)",
            enabled = true,
            provider = INSTAGRAM,
            uri = "originaltrollfootball"
    )
}