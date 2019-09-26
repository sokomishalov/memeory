package ru.sokomishalov.memeory.service.provider.vk.scrape

import ru.sokomishalov.memeory.autoconfigure.BaseBooleanPropertyCondition


/**
 * @author sokomishalov
 */
class VkScrapeCondition : BaseBooleanPropertyCondition() {
    override val propertyName: String = "provider.vk.scrape-enabled"
}
