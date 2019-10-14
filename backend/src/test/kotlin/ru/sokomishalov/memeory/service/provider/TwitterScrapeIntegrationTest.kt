package ru.sokomishalov.memeory.service.provider

import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.enums.Provider.TWITTER

/**
 * @author sokomishalov
 */
class TwitterScrapeIntegrationTest : AbstractProviderIntegrationTest() {
    override val channel: ChannelDTO = ChannelDTO(
            id = "twitter:russianmemesltd",
            name = "Russian Memes United (Twitter)",
            enabled = true,
            provider = TWITTER,
            uri = "russianmemesltd"
    )
}