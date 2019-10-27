package ru.sokomishalov.memeory.api.config

import org.apache.commons.lang3.RandomStringUtils.randomAlphabetic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.OPTIONS
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import ru.sokomishalov.commons.core.log.Loggable
import ru.sokomishalov.commons.spring.config.CustomWebFluxConfigurer
import ru.sokomishalov.memeory.api.autoconfigure.MemeoryProperties
import ru.sokomishalov.memeory.core.dto.AdminUserDTO

/**
 * @author sokomishalov
 */
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class WebFluxConfig : CustomWebFluxConfigurer(), Loggable {

    companion object {
        private const val ADMIN_ROLE = "ADMIN"
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder(10)

    @Bean
    fun userDetailsService(passwordEncoder: PasswordEncoder, props: MemeoryProperties): ReactiveUserDetailsService? {
        return MapReactiveUserDetailsService(*props
                .admins
                .ifEmpty {
                    listOf(AdminUserDTO()
                            .apply {
                                val generatedUsername = randomAlphabetic(5, 8).toLowerCase()
                                val generatedPassword = randomAlphabetic(10, 15).toLowerCase()
                                log("No admin users specified, generated one = { $generatedUsername : $generatedPassword }")
                                username = generatedUsername
                                password = generatedPassword
                            }
                    )
                }
                .map {
                    User
                            .builder()
                            .username(it.username)
                            .password(it.password)
                            .roles(ADMIN_ROLE)
                            .passwordEncoder(passwordEncoder::encode)
                            .build()
                }
                .toTypedArray()
        )
    }

    @Bean
    fun filterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
                .authorizeExchange()
                .pathMatchers(OPTIONS, "/**").permitAll()
                .pathMatchers(
                        "/",
                        "/csrf",
                        "/login",
                        "/swagger-ui.html",
                        "/v2/api-docs",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/memes/**",
                        "/profile/**",
                        "/channels/list",
                        "/channels/list/enabled",
                        "/channels/logo/*"
                ).permitAll()
                .anyExchange().authenticated()
                .and()
                .httpBasic()
                .and()
                .formLogin()
                .and()
                .csrf().disable()
                .cors().disable()
                .build()
    }
}
