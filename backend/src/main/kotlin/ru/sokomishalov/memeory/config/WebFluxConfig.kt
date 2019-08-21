package ru.sokomishalov.memeory.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.OPTIONS
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.reactive.config.WebFluxConfigurer
import ru.sokomishalov.memeory.util.COROUTINE_PATH_PREFIX
import ru.sokomishalov.memeory.util.serialization.OBJECT_MAPPER
import org.springframework.security.core.userdetails.User.builder as userBuilder

/**
 * @author sokomishalov
 */
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class WebFluxConfig : WebFluxConfigurer {

    override fun configureHttpMessageCodecs(configurer: ServerCodecConfigurer) {
        configurer.defaultCodecs().jackson2JsonEncoder(jackson2JsonEncoder())
        configurer.defaultCodecs().jackson2JsonDecoder(jackson2JsonDecoder())
    }

    @Bean
    fun jackson2JsonEncoder(): Jackson2JsonEncoder = Jackson2JsonEncoder(OBJECT_MAPPER)

    @Bean
    fun jackson2JsonDecoder(): Jackson2JsonDecoder = Jackson2JsonDecoder(OBJECT_MAPPER)

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder(10)

    // FIXME
    @Bean
    fun userDetailsService(passwordEncoder: PasswordEncoder): MapReactiveUserDetailsService =
            MapReactiveUserDetailsService(userBuilder()
                    .username("admin")
                    .password("admin")
                    .roles("ADMIN")
                    .passwordEncoder { passwordEncoder.encode(it) }
                    .build()
            )

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
