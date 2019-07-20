@file:Suppress("unused")

package ru.sokomishalov.memeory.util

/**
 * @author sokomishalov
 */

fun CharSequence?.isNotNullOrBlank(): Boolean {
    return (this == null || this.isBlank()).not()
}

fun <K, V> Map<out K, V>?.isNotNullOrEmpty(): Boolean {
    return (this == null || isEmpty()).not()
}

fun <K, V> Map<out K, V>?.isNullOrEmpty(): Boolean {
    return this == null || isEmpty()
}

infix fun <K, V> Map<K, V>.containsEntryFrom(other: Map<K, V>): Boolean {
    return this.entries.intersect(other.entries).isNullOrEmpty().not()
}