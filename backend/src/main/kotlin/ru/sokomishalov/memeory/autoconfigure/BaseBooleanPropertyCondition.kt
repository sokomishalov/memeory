@file:Suppress("MemberVisibilityCanBePrivate")

package ru.sokomishalov.memeory.autoconfigure

import org.springframework.context.annotation.Condition
import org.springframework.context.annotation.ConditionContext
import org.springframework.core.type.AnnotatedTypeMetadata
import java.lang.Boolean.parseBoolean

/**
 * @author sokomishalov
 */
abstract class BaseBooleanPropertyCondition : Condition {
    protected abstract val propertyName: String
    protected val matchIfMissing: Boolean = false

    override fun matches(conditionContext: ConditionContext, annotatedTypeMetadata: AnnotatedTypeMetadata): Boolean {
        return runCatching { parseBoolean(conditionContext.environment.getProperty(propertyName)) }
                .getOrElse { matchIfMissing }
    }
}