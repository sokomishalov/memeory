package ru.sokomishalov.memeory.service.provider.ninegag

import ru.sokomishalov.memeory.autoconfigure.condition.BaseBooleanPropertyCondition


/**
 * @author sokomishalov
 */
class NinegagCondition : BaseBooleanPropertyCondition() {
    override val propertyName: String = "provider.ninegag.enabled"
}
