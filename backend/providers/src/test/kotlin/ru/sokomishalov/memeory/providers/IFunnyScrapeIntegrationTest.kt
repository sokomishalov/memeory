package ru.sokomishalov.memeory.providers

import ru.sokomishalov.memeory.core.dto.ChannelDTO
import ru.sokomishalov.memeory.core.enums.Provider.IFUNNY


/**
 * @author sokomishalov
 */
class IFunnyScrapeIntegrationTest : AbstractProviderIntegrationTest() {
    override val channel: ChannelDTO = ChannelDTO(
            id = "ifunny:user:memes",
            name = "memes user (ifunny)",
            provider = IFUNNY,
            uri = "memes"
    )
}