package ru.sokomishalov.memeory.service.provider

import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.enums.Provider.VK

/**
 * @author sokomishalov
 */
class VkScrapeIntegrationTest : AbstractProviderIntegrationTest() {
    override val channel: ChannelDTO = ChannelDTO(
            id = "vk:vgik",
            name = "ВГИК (VK)",
            enabled = true,
            provider = VK,
            uri = "komment"
    )
}