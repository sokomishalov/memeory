package ru.sokomishalov.memeory.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import java.net.URI.create

/**
 * @author sokomishalov
 */
@Configuration
class SwaggerIndexController {

    @Bean
    fun route() = router {
        GET("/") { ServerResponse.permanentRedirect(create("/swagger-ui.html")).build() }
    }
}
