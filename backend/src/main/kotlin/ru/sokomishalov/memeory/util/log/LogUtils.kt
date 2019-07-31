package ru.sokomishalov.memeory.util.log

import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger
import java.lang.System.currentTimeMillis as systemCurrentTimeMillis

/**
 * @author sokomishalov
 */

fun <T> loggerFor(clazz: Class<T>): Logger = getLogger(clazz.simpleName)

inline fun measureTime(pretext: String = "Execution time", block: () -> Unit) {
    val start = systemCurrentTimeMillis()
    block()
    val time = systemCurrentTimeMillis() - start
    println("$pretext : $time ms")
}
