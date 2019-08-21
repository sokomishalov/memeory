package ru.sokomishalov.memeory.web.coroutine

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter
import ru.sokomishalov.memeory.condition.ConditionalOnUsingCoroutines
import ru.sokomishalov.memeory.util.consts.COROUTINE_PATH_PREFIX
import ru.sokomishalov.memeory.util.extensions.awaitStrict
import java.net.URI.create


/**
 * @author sokomishalov
 */

@Configuration
class MainCoroutineRouter(
        private val memeHandler: CoroutineMemeHandler,
        private val profileHandler: CoroutineProfileHandler,
        private val channelHandler: CoroutineChannelHandler
) {

    @Bean
    @ConditionalOnUsingCoroutines
    fun router() = coRouter {
        GET("/") {
            permanentRedirect(create("/swagger-ui.html")).build().awaitStrict()
        }

        COROUTINE_PATH_PREFIX.nest {
            "/memes".nest {
                GET("/page/{page}/{count}", memeHandler::pageOfMemes)
            }
            "/profile".nest {
                POST("/save", profileHandler::saveProfile)
            }
            "/channels".nest {
                GET("/list", channelHandler::findAll)
                GET("/list/enabled", channelHandler::findAllEnabled)
                GET("/logo/{channelId}", channelHandler::getChannelLogo)
                POST("/enable", channelHandler::enableChannel)
                POST("/disable", channelHandler::disableChannel)
                POST("/add", channelHandler::addChannel)
            }
        }
    }
}
