package ru.sokomishalov.memeory.service.provider

import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.enums.SourceType.VK

/**
 * @author sokomishalov
 */
class VkScrapeIntegrationTest : AbstractProviderIntegrationTest() {
    override val channel: ChannelDTO = ChannelDTO(
            id = "vk:vgik",
            name = "ВГИК (VK)",
            enabled = true,
            sourceType = VK,
            uri = "komment"
    )
}