package ru.sokomishalov.memeory.service.provider

import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.enums.Provider.INSTAGRAM

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