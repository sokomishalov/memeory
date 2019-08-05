package ru.sokomishalov.memeory.condition

import org.springframework.context.annotation.ConditionContext
import org.springframework.context.annotation.Conditional
import org.springframework.core.type.AnnotatedTypeMetadata
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.FUNCTION


/**
 * @author sokomishalov
 */

@Target(CLASS, FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Conditional(UsingCoroutinesCondition::class)
annotation class ConditionalOnUsingCoroutines

@Target(CLASS, FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Conditional(NotUsingCoroutinesCondition::class)
annotation class ConditionalOnNotUsingCoroutines


const val PROPERTY_NAME = "memeory.enable-coroutines"

class UsingCoroutinesCondition : BaseBooleanPropertyCondition() {
    override val propertyName: String = PROPERTY_NAME
}

class NotUsingCoroutinesCondition : BaseBooleanPropertyCondition() {
    override val propertyName: String = PROPERTY_NAME

    override fun matches(conditionContext: ConditionContext, annotatedTypeMetadata: AnnotatedTypeMetadata): Boolean {
        return super.matches(conditionContext, annotatedTypeMetadata).not()
    }
}
