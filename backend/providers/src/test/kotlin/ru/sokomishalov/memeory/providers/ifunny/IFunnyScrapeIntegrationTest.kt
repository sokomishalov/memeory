package ru.sokomishalov.memeory.providers.ifunny

import ru.sokomishalov.memeory.core.dto.ChannelDTO
import ru.sokomishalov.memeory.core.enums.Provider.IFUNNY
import ru.sokomishalov.memeory.providers.AbstractProviderIntegrationTest
import ru.sokomishalov.memeory.providers.ProviderService
import ru.sokomishalov.memeory.providers.impls.ifunny.IFunnyProviderService


/**
 * @author sokomishalov
 */
class IFunnyScrapeIntegrationTest : AbstractProviderIntegrationTest() {
    override val service: ProviderService = IFunnyProviderService()
    override val channel: ChannelDTO = ChannelDTO(
            id = "ifunny:user:memes",
            name = "memes user (ifunny)",
            provider = IFUNNY,
            uri = "memes"
    )
}