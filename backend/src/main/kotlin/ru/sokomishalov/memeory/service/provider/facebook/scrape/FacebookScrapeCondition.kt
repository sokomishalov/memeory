package ru.sokomishalov.memeory.service.provider.facebook.scrape

import ru.sokomishalov.memeory.autoconfigure.condition.BaseBooleanPropertyCondition


/**
 * @author sokomishalov
 */
class FacebookScrapeCondition : BaseBooleanPropertyCondition() {
    override val propertyName: String = "provider.facebook.scrape-enabled"
}
