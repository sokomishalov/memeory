package ru.sokomishalov.memeory.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders.AUTHORIZATION
import ru.sokomishalov.commons.spring.swagger.customizeDocket
import ru.sokomishalov.memeory.api.MemeoryApplication
import springfox.documentation.builders.RequestHandlerSelectors.basePackage
import springfox.documentation.service.AuthorizationScope
import springfox.documentation.service.BasicAuth
import springfox.documentation.service.SecurityReference
import springfox.documentation.service.SecurityScheme
import springfox.documentation.spi.DocumentationType.SWAGGER_2
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux
import springfox.documentation.builders.PathSelectors.any as anyPath

/**
 * @author sokomishalov
 */
@Configuration
@EnableSwagger2WebFlux
class SwaggerConfig {

    @Bean
    fun customImplementation(): Docket =
            Docket(SWAGGER_2)
                    .select()
                    .apis(basePackage(MemeoryApplication::class.java.`package`.name))
                    .paths(anyPath())
                    .build()
                    .customizeDocket(
                            title = "Memeory API",
                            securityContext = securityContext(),
                            securityScheme = securityScheme()
                    )

    private fun securityScheme(): SecurityScheme? =
            BasicAuth("basic")

    private fun securityContext(): SecurityContext =
            SecurityContext
                    .builder()
                    .forPaths { true }
                    .securityReferences(listOf(SecurityReference(AUTHORIZATION, arrayOf(AuthorizationScope("global", "Access all")))))
                    .build()
}
