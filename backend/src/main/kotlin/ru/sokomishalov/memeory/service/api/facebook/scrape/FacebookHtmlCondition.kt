package ru.sokomishalov.memeory.service.api.facebook.scrape

import ru.sokomishalov.memeory.condition.BaseBooleanPropertyCondition


/**
 * @author sokomishalov
 */
class FacebookHtmlCondition : BaseBooleanPropertyCondition() {
    override val propertyName: String = "channel.facebook.scrape-enabled"
}
