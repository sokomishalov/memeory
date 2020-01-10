package ru.sokomishalov.memeory.providers.instagram

import ru.sokomishalov.memeory.core.dto.ChannelDTO
import ru.sokomishalov.memeory.core.enums.Provider.INSTAGRAM
import ru.sokomishalov.memeory.providers.AbstractProviderIntegrationTest
import ru.sokomishalov.memeory.providers.ProviderService
import ru.sokomishalov.memeory.providers.impls.instagram.InstagramProviderService

/**
 * @author sokomishalov
 */
class InstagramScrapeIntegrationTest : AbstractProviderIntegrationTest() {
    override val service: ProviderService = InstagramProviderService()
    override val channel: ChannelDTO = ChannelDTO(
            id = "instagram:originaltrollfootball",
            name = "Troll Football (Instagram)",
            provider = INSTAGRAM,
            uri = "originaltrollfootball"
    )
}