package ru.sokomishalov.memeory.service.provider.vk.api

import org.springframework.context.annotation.ConditionContext
import org.springframework.core.type.AnnotatedTypeMetadata
import ru.sokomishalov.memeory.condition.BaseBooleanPropertyCondition


/**
 * @author sokomishalov
 */
class VkApiCondition : BaseBooleanPropertyCondition() {
    override val propertyName: String = "provider.vk.scrape-enabled"

    override fun matches(conditionContext: ConditionContext, annotatedTypeMetadata: AnnotatedTypeMetadata): Boolean {
        return super.matches(conditionContext, annotatedTypeMetadata).not()
    }
}
