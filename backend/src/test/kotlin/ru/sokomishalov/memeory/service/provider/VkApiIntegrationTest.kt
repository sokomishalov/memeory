package ru.sokomishalov.memeory.service.provider

import org.springframework.test.context.TestPropertySource
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.enums.SourceType.VK

/**
 * @author sokomishalov
 */
@TestPropertySource(properties = ["provider.vk.scrape-enabled=false"])
class VkApiIntegrationTest : AbstractProviderIntegrationTest() {
    override val channel: ChannelDTO = ChannelDTO(
            id = "vk:vgik",
            name = "ВГИК (VK)",
            enabled = true,
            sourceType = VK,
            uri = "komment"
    )
}