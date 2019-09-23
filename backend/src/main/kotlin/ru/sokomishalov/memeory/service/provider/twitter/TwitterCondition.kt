package ru.sokomishalov.memeory.service.provider.twitter

import ru.sokomishalov.memeory.autoconfigure.condition.BaseBooleanPropertyCondition


/**
 * @author sokomishalov
 */
class TwitterCondition : BaseBooleanPropertyCondition() {
    override val propertyName: String = "provider.twitter.enabled"
}
