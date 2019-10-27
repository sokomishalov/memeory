package ru.sokomishalov.memeory.providers

import ru.sokomishalov.memeory.core.dto.ChannelDTO
import ru.sokomishalov.memeory.core.enums.Provider.FACEBOOK

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