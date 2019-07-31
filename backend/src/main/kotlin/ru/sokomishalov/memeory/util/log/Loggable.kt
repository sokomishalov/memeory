@file:Suppress("JAVA_CLASS_ON_COMPANION", "unused")

package ru.sokomishalov.memeory.util.log

import org.slf4j.Logger

interface Loggable {

    fun log(s: String?) = logInfo(s)

    fun logInfo(s: String?) = logger().info(s)

    fun logWarn(message: String?) = logger().warn(message)

    fun logError(t: Throwable) = logError(t.message, t)

    fun logError(message: String?, t: Throwable) = logger().error(message, t)

}

internal fun <T : Loggable> T.logger(): Logger = loggerFor(javaClass)
