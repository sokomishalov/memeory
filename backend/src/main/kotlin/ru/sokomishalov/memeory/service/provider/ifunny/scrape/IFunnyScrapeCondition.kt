package ru.sokomishalov.memeory.service.provider.ifunny.scrape

import ru.sokomishalov.memeory.autoconfigure.BaseBooleanPropertyCondition


/**
 * @author sokomishalov
 */
class IFunnyScrapeCondition : BaseBooleanPropertyCondition() {
    override val propertyName: String = "provider.ifunny.scrape-enabled"
}
