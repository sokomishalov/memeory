package ru.sokomishalov.memeory.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router
import ru.sokomishalov.memeory.condition.ConditionalOnNotUsingCoroutines
import java.net.URI.create

/**
 * @author sokomishalov
 */
@Configuration
class SwaggerIndexController {

    @Bean
    @ConditionalOnNotUsingCoroutines
    fun route() = router {
        GET("/") { permanentRedirect(create("/swagger-ui.html")).build() }
    }
}
