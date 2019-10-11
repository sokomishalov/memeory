package ru.sokomishalov.memeory.service.provider.ifunny

import ru.sokomishalov.memeory.autoconfigure.BaseBooleanPropertyCondition


/**
 * @author sokomishalov
 */
class IFunnyCondition : BaseBooleanPropertyCondition() {
    override val propertyName: String = "provider.ifunny.enabled"
}
