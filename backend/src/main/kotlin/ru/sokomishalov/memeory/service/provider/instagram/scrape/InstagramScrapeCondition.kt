package ru.sokomishalov.memeory.service.provider.instagram.scrape

import ru.sokomishalov.memeory.condition.BaseBooleanPropertyCondition


/**
 * @author sokomishalov
 */
class InstagramScrapeCondition : BaseBooleanPropertyCondition() {
    override val propertyName: String = "provider.instagram.scrape-enabled"
}
