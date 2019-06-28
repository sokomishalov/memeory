package ru.sokomishalov.memeory.service.provider.vk

import ru.sokomishalov.memeory.condition.BaseBooleanPropertyCondition


/**
 * @author sokomishalov
 */
class VkCondition : BaseBooleanPropertyCondition() {
    override val propertyName: String = "provider.vk.enabled"
}
