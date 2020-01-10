package ru.sokomishalov.memeory.providers.vk

import ru.sokomishalov.memeory.core.dto.ChannelDTO
import ru.sokomishalov.memeory.core.enums.Provider.VK
import ru.sokomishalov.memeory.providers.AbstractProviderIntegrationTest
import ru.sokomishalov.memeory.providers.ProviderService
import ru.sokomishalov.memeory.providers.impls.vk.VkProviderService


/**
 * @author sokomishalov
 */
class VkScrapeIntegrationTest : AbstractProviderIntegrationTest() {
    override val service: ProviderService = VkProviderService()
    override val channel: ChannelDTO = ChannelDTO(
            id = "vk:vgik",
            name = "ВГИК (VK)",
            provider = VK,
            uri = "komment"
    )
}