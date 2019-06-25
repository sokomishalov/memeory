package ru.sokomishalov.memeory.condition

import org.springframework.context.annotation.Condition
import org.springframework.context.annotation.ConditionContext
import org.springframework.core.type.AnnotatedTypeMetadata
import java.lang.Boolean.parseBoolean

/**
 * @author sokomishalov
 */
abstract class BaseBooleanPropertyCondition : Condition {

    protected abstract val propertyName: String

    override fun matches(conditionContext: ConditionContext, annotatedTypeMetadata: AnnotatedTypeMetadata): Boolean {
        return try {
            val property = conditionContext.environment.getProperty(propertyName)
            parseBoolean(property)
        } catch (ignored: Exception) {
            false
        }
    }
}
