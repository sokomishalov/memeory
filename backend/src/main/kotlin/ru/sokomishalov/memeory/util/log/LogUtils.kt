package ru.sokomishalov.memeory.util.log

import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger
import java.lang.System.currentTimeMillis as systemCurrentTimeMillis

/**
 * @author sokomishalov
 */

fun <T> loggerFor(clazz: Class<T>): Logger = getLogger(clazz.simpleName)

inline fun <T> measureTimeAndReturn(preCaption: String = "Execution time", block: () -> T): T {
    val start = systemCurrentTimeMillis()
    val res = block()
    val time = systemCurrentTimeMillis() - start
    println("$preCaption : $time ms")
    return res
}
