package ru.sokomishalov.memeory.service.provider

import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.enums.Provider.FACEBOOK

/**
 * @author sokomishalov
 */
class FacebookScrapeIntegrationTest : AbstractProviderIntegrationTest() {
    override val channel: ChannelDTO = ChannelDTO(
            id = "facebook:originaltrollfootball",
            name = "Troll Football (Facebook)",
            enabled = true,
            provider = FACEBOOK,
            uri = "originaltrollfootball"
    )
}