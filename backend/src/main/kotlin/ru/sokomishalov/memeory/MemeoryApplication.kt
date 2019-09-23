package ru.sokomishalov.memeory

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * @author sokomishalov
 */

@SpringBootApplication
class MemeoryApplication

fun main(args: Array<String>) {
    runApplication<MemeoryApplication>(*args)
}
