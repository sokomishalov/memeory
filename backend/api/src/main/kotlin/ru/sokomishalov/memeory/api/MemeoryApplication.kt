package ru.sokomishalov.memeory.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import ru.sokomishalov.memeory.heroku.keepalive.keepAliveHerokuApp
import ru.sokomishalov.memeory.telegram.util.api.initTelegramApi
import java.time.ZoneOffset

/**
 * @author sokomishalov
 */

@SpringBootApplication(scanBasePackages = ["ru.sokomishalov.memeory"])
class MemeoryApplication

fun main(args: Array<String>) {
    initTelegramApi()
    runApplication<MemeoryApplication>(*args)
    keepAliveHerokuApp(appName = "memeory-backend", offset = ZoneOffset.ofHours(3))
}
