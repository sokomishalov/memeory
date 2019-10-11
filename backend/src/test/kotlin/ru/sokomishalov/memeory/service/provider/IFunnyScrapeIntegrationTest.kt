package ru.sokomishalov.memeory.service.provider

import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.enums.SourceType.IFUNNY

/**
 * @author sokomishalov
 */
class IFunnyScrapeIntegrationTest : AbstractProviderIntegrationTest() {
    override val channel: ChannelDTO = ChannelDTO(
            id = "ifunny:user:memes",
            name = "memes user (ifunny)",
            enabled = true,
            sourceType = IFUNNY,
            uri = "memes"
    )
}