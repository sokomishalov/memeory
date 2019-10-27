package ru.sokomishalov.memeory.core.util.time

import org.apache.commons.lang3.time.DateUtils.addMilliseconds
import java.time.Duration
import java.time.Duration.ofMinutes
import java.util.*

/**
 * @author sokomishalov
 */

fun mockDate(
        memeIndex: Int = 0,
        from: Date = Date(),
        minus: Duration = ofMinutes(10)
): Date {
    return addMilliseconds(from, -memeIndex * minus.toMillis().toInt())
}