package ru.sokomishalov.memeory.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import ru.sokomishalov.memeory.telegram.util.api.initTelegramApi

/**
 * @author sokomishalov
 */

@SpringBootApplication(scanBasePackages = ["ru.sokomishalov.memeory"])
class MemeoryApplication

fun main(args: Array<String>) {
    initTelegramApi()
    runApplication<MemeoryApplication>(*args)
}
