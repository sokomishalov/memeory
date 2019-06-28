package ru.sokomishalov.memeory

import org.slf4j.Logger
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import reactor.tools.agent.ReactorDebugAgent.init
import reactor.tools.agent.ReactorDebugAgent.processExistingClasses
import ru.sokomishalov.memeory.config.props.MemeoryProperties
import ru.sokomishalov.memeory.util.loggerFor

/**
 * @author sokomishalov
 */

@SpringBootApplication
@EnableReactiveMongoRepositories
@EnableConfigurationProperties(MemeoryProperties::class)
class MemeoryApplication

private val log: Logger = loggerFor(MemeoryApplication::class.java)

fun main(args: Array<String>) {
    initReactorDebugTools()
    runApplication<MemeoryApplication>(*args)
}

private fun initReactorDebugTools() {
    try {
        init()
        processExistingClasses()

        log.info("ReactorDebugAgent started")
    } catch (e: Throwable) {
        log.warn("ReactorDebugAgent failed to start: ${e.message}")
    }

}

