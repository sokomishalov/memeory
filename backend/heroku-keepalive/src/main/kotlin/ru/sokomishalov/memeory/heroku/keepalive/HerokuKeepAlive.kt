@file:JvmName("HerokuApp")

package ru.sokomishalov.memeory.heroku.keepalive

import kotlinx.coroutines.GlobalScope
import ru.sokomishalov.commons.core.http.REACTIVE_NETTY_HTTP_CLIENT
import ru.sokomishalov.commons.core.reactor.await
import ru.sokomishalov.commons.core.scheduled.runScheduled
import java.time.*

/**
 * @author sokomishalov
 */

@JvmOverloads
@JvmName("keepAlive")
fun keepAliveHerokuApp(
        appName: String,
        uri: String = "",
        interval: Duration = Duration.ofMinutes(5),
        offset: ZoneOffset = ZoneOffset.UTC,
        bedTime: OffsetTime = LocalTime.MIDNIGHT.atOffset(offset),
        wakeUpTime: OffsetTime = bedTime + Duration.ofHours(6)
) {
    GlobalScope.runScheduled(interval = interval) {
        val currentDate = LocalDate.now(ZoneId.of(offset.id))

        val bedZdt = bedTime.atDate(currentDate)
        val wakeUpZdt = wakeUpTime.atDate(currentDate)
        val nowHere = OffsetDateTime.now(ZoneId.of(offset.id))

        if (nowHere.isBefore(bedZdt) or nowHere.isAfter(wakeUpZdt)) {
            REACTIVE_NETTY_HTTP_CLIENT.get().uri("https://${appName}.herokuapp.com/${uri}").response().await()
        }
    }
}