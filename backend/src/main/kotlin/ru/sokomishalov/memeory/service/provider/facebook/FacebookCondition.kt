package ru.sokomishalov.memeory.service.provider.facebook

import ru.sokomishalov.memeory.condition.BaseBooleanPropertyCondition


/**
 * @author sokomishalov
 */
class FacebookCondition : BaseBooleanPropertyCondition() {
    override val propertyName: String = "provider.facebook.enabled"
}
