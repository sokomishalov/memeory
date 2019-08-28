package ru.sokomishalov.memeory.service.provider

import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.enums.SourceType.INSTAGRAM

/**
 * @author sokomishalov
 */
class InstagramScrapeIntegrationTest : AbstractProviderIntegrationTest() {
    override val channel: ChannelDTO = ChannelDTO(
            id = "instagram:originaltrollfootball",
            name = "Troll Football (Instagram)",
            enabled = true,
            sourceType = INSTAGRAM,
            uri = "originaltrollfootball"
    )
}