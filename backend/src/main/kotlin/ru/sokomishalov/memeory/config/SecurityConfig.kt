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
import org.springframework.web.reactive.config.WebFluxConfigurer
import ru.sokomishalov.memeory.util.COROUTINE_PATH_PREFIX

/**
 * @author sokomishalov
 */
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfig : WebFluxConfigurer {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder(10)

    @Bean
    fun userDetailsService(passwordEncoder: PasswordEncoder): MapReactiveUserDetailsService {
        val user = User
                .builder()
                .username("admin")
                .password("admin")
                .roles("ADMIN")
                .passwordEncoder { passwordEncoder.encode(it) }
                .build()

        return MapReactiveUserDetailsService(user)
    }

    @Bean
    fun filterChain(http: ServerHttpSecurity): SecurityWebFilterChain = http
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
                    "/channels/logo/*",
                    "$COROUTINE_PATH_PREFIX/**"
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
