package ru.sokomishalov.memeory.service.provider.facebook.graphapi

import org.springframework.context.annotation.ConditionContext
import org.springframework.core.type.AnnotatedTypeMetadata
import ru.sokomishalov.memeory.condition.BaseBooleanPropertyCondition


/**
 * @author sokomishalov
 */
class FacebookGraphApiCondition : BaseBooleanPropertyCondition() {
    override val propertyName: String = "provider.facebook.scrape-enabled"

    override fun matches(conditionContext: ConditionContext, annotatedTypeMetadata: AnnotatedTypeMetadata): Boolean {
        return super.matches(conditionContext, annotatedTypeMetadata).not()
    }
}
