package ru.sokomishalov.memeory.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger


/**
 * @author sokomishalov
 */

fun <T> loggerFor(clazz: Class<T>): Logger = getLogger(clazz.simpleName)
