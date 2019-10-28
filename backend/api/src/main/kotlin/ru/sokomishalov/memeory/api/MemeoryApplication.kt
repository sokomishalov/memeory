package ru.sokomishalov.memeory.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * @author sokomishalov
 */

@SpringBootApplication(scanBasePackages = ["ru.sokomishalov.memeory"])
class MemeoryApplication

fun main(args: Array<String>) {
    runApplication<MemeoryApplication>(*args)
}
