package ru.sokomishalov.memeory.service.api.instagram

import ru.sokomishalov.memeory.condition.BaseBooleanPropertyCondition


/**
 * @author sokomishalov
 */
class InstagramCondition : BaseBooleanPropertyCondition() {
    override val propertyName: String = "channel.instagram.enabled"
}
