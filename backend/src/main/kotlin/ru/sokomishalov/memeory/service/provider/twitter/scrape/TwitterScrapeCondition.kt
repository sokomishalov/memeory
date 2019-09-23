package ru.sokomishalov.memeory.service.provider.twitter.scrape

import ru.sokomishalov.memeory.autoconfigure.condition.BaseBooleanPropertyCondition


/**
 * @author sokomishalov
 */
class TwitterScrapeCondition : BaseBooleanPropertyCondition() {
    override val propertyName: String = "provider.twitter.scrape-enabled"
}
