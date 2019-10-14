package ru.sokomishalov.memeory.service.provider

import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.enums.Provider.IFUNNY

/**
 * @author sokomishalov
 */
class IFunnyScrapeIntegrationTest : AbstractProviderIntegrationTest() {
    override val channel: ChannelDTO = ChannelDTO(
            id = "ifunny:user:memes",
            name = "memes user (ifunny)",
            enabled = true,
            provider = IFUNNY,
            uri = "memes"
    )
}