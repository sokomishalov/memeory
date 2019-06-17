package ru.sokomishalov.memeory.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory


/**
 * @author sokomishalov
 */

fun <T> loggerFor(clazz: Class<T>): Logger = LoggerFactory.getLogger(clazz.simpleName)
