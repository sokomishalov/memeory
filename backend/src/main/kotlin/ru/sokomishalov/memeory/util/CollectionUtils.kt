package ru.sokomishalov.memeory.util


/**
 * @author sokomishalov
 */

infix fun <K, V> Map<K, V>.containsEntryFrom(other: Map<K, V>): Boolean {
    return this.entries.intersect(other.entries).isNullOrEmpty().not()
}
