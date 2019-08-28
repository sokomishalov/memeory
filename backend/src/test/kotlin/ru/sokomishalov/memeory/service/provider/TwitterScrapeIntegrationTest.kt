package ru.sokomishalov.memeory.service.provider

import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.enums.SourceType.TWITTER

/**
 * @author sokomishalov
 */
class TwitterScrapeIntegrationTest : AbstractProviderIntegrationTest() {
    override val channel: ChannelDTO = ChannelDTO(
            id = "twitter:russianmemesltd",
            name = "Russian Memes United (Twitter)",
            enabled = true,
            sourceType = TWITTER,
            uri = "russianmemesltd"
    )
}