package ru.sokomishalov.memeory.providers.facebook

import ru.sokomishalov.memeory.core.dto.ChannelDTO
import ru.sokomishalov.memeory.core.enums.Provider.FACEBOOK
import ru.sokomishalov.memeory.providers.AbstractProviderIntegrationTest
import ru.sokomishalov.memeory.providers.ProviderService
import ru.sokomishalov.memeory.providers.impls.facebook.FacebookProviderService

/**
 * @author sokomishalov
 */
class FacebookScrapeIntegrationTest : AbstractProviderIntegrationTest() {
    override val service: ProviderService = FacebookProviderService()
    override val channel: ChannelDTO = ChannelDTO(
            id = "facebook:originaltrollfootball",
            name = "Troll Football (Facebook)",
            provider = FACEBOOK,
            uri = "originaltrollfootball"
    )
}