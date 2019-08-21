package ru.sokomishalov.memeory.service.provider.vk.scrape

import ru.sokomishalov.memeory.condition.BaseBooleanPropertyCondition


/**
 * @author sokomishalov
 */
class VkScrapeCondition : BaseBooleanPropertyCondition() {
    override val propertyName: String = "provider.vk.scrape-enabled"
}
