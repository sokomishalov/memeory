package ru.sokomishalov.memeory.service.provider.twitter

import ru.sokomishalov.memeory.autoconfigure.BaseBooleanPropertyCondition


/**
 * @author sokomishalov
 */
class TwitterCondition : BaseBooleanPropertyCondition() {
    override val propertyName: String = "provider.twitter.enabled"
}
