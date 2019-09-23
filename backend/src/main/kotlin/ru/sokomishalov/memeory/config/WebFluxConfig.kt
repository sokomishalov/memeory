package ru.sokomishalov.memeory.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.OPTIONS
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import ru.sokomishalov.commons.spring.config.CustomWebFluxConfigurer

/**
 * @author sokomishalov
 */
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class WebFluxConfig : CustomWebFluxConfigurer() {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder(10)

    // FIXME
    @Bean
    fun userDetailsService(passwordEncoder: PasswordEncoder): MapReactiveUserDetailsService =
            MapReactiveUserDetailsService(User
                    .builder()
                    .username("admin")
                    .password("admin")
                    .roles("ADMIN")
                    .passwordEncoder { passwordEncoder.encode(it) }
                    .build()
            )

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
