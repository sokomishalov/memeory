package ru.sokomishalov.memeory.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import ru.sokomishalov.memeory.MemeoryApplication
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.RequestHandlerSelectors.basePackage
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType.SWAGGER_2
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux
import springfox.documentation.builders.PathSelectors.any as anyPath
import springfox.documentation.spi.service.contexts.SecurityContext.builder as securityContextBuilder

/**
 * @author sokomishalov
 */
@Configuration
@EnableSwagger2WebFlux
@Order(2)
class SwaggerConfig {

    @Bean
    fun customImplementation(): Docket =
            Docket(SWAGGER_2)
                    .select()
                    .apis(basePackage(MemeoryApplication::class.java.packageName))
                    .paths(anyPath())
                    .build()
                    .securitySchemes(listOf(securityScheme()))
                    .securityContexts(listOf(securityContext()))
                    .ignoredParameterTypes(ServerHttpRequest::class.java)
                    .genericModelSubstitutes(ResponseEntity::class.java)
                    .apiInfo(ApiInfoBuilder()
                            .title("Memeory API")
                            .license("Apache 2.0")
                            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                            .contact(Contact("Sokolov Mikhael", "https://t.me/sokomishalov", "sokomishalov@mail.ru"))
                            .termsOfServiceUrl("")
                            .version("0.0.1")
                            .build()
                    )

    private fun securityScheme(): SecurityScheme? =
            BasicAuth("basic")

    private fun securityContext(): SecurityContext =
            securityContextBuilder()
                    .forPaths(anyPath())
                    .securityReferences(listOf(SecurityReference(AUTHORIZATION, arrayOf(AuthorizationScope("global", "Access all")))))
                    .build()
}
