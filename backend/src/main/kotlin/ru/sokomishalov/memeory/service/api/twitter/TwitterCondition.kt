package ru.sokomishalov.memeory.service.api.twitter

import ru.sokomishalov.memeory.condition.BaseBooleanPropertyCondition


/**
 * @author sokomishalov
 */
class TwitterCondition : BaseBooleanPropertyCondition() {
    override val propertyName: String = "channel.twitter.enabled"
}
