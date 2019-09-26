package ru.sokomishalov.memeory.service.provider.pinterest.scrape

import ru.sokomishalov.memeory.autoconfigure.BaseBooleanPropertyCondition


/**
 * @author sokomishalov
 */
class PinterestScrapeCondition : BaseBooleanPropertyCondition() {
    override val propertyName: String = "provider.pinterest.scrape-enabled"
}
