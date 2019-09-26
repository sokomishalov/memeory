package ru.sokomishalov.memeory.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router
import ru.sokomishalov.commons.spring.swagger.redirectRootToSwagger

/**
 * @author sokomishalov
 */
@Configuration
class SwaggerIndexController {
    @Bean
    fun router() = router {
        redirectRootToSwagger()
    }
}