package ru.sokomishalov.memeory.providers

import ru.sokomishalov.memeory.core.dto.ChannelDTO
import ru.sokomishalov.memeory.core.enums.Provider.TWITTER

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