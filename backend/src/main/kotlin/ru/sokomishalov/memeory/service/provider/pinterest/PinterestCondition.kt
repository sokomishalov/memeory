package ru.sokomishalov.memeory.service.provider.pinterest

import ru.sokomishalov.memeory.condition.BaseBooleanPropertyCondition


/**
 * @author sokomishalov
 */
class PinterestCondition : BaseBooleanPropertyCondition() {
    override val propertyName: String = "provider.pinterest.enabled"
}
