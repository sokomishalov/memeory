package ru.sokomishalov.memeory.service.api.vk

import ru.sokomishalov.memeory.condition.BaseBooleanPropertyCondition


/**
 * @author sokomishalov
 */
class VkCondition : BaseBooleanPropertyCondition() {
    override val propertyName: String = "channel.vk.enabled"
}
