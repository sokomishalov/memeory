package ru.sokomishalov.memeory.service.provider.ninegag

import ru.sokomishalov.memeory.condition.BaseBooleanPropertyCondition


/**
 * @author sokomishalov
 */
class NinegagCondition : BaseBooleanPropertyCondition() {
    override val propertyName: String = "provider.ninegag.enabled"
}
