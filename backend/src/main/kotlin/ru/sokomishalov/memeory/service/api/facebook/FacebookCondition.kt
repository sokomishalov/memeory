package ru.sokomishalov.memeory.service.api.facebook

import ru.sokomishalov.memeory.condition.BaseBooleanPropertyCondition


/**
 * @author sokomishalov
 */
class FacebookCondition : BaseBooleanPropertyCondition() {
    override val propertyName: String = "channel.facebook.enabled"
}
