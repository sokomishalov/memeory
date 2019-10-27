package ru.sokomishalov.memeory.providers

import ru.sokomishalov.memeory.core.dto.ChannelDTO
import ru.sokomishalov.memeory.core.enums.Provider.VK


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