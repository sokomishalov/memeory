@file:Suppress("NOTHING_TO_INLINE")

package ru.sokomishalov.memeory.providers.util.time

import java.time.Duration
import java.util.*

/**
 * @author sokomishalov
 */

@PublishedApi
internal inline fun mockDate(
        memeIndex: Int = 0,
        from: Date = Date(),
        minusMultiply: Duration = Duration.ofMinutes(10)
): Date {
    return Date.from(from.toInstant() - (minusMultiply.multipliedBy(memeIndex.toLong())))
}