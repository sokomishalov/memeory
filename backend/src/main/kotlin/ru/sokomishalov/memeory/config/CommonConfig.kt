package ru.sokomishalov.memeory.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.io.IOUtils.toByteArray
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.io.Resource
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.web.reactive.function.client.WebClient
import ru.sokomishalov.commons.core.serialization.OBJECT_MAPPER
import ru.sokomishalov.commons.spring.serialization.JACKSON_DECODER
import ru.sokomishalov.commons.spring.serialization.JACKSON_ENCODER
import ru.sokomishalov.commons.spring.webclient.REACTIVE_WEB_CLIENT
import ru.sokomishalov.memeory.autoconfigure.MemeoryProperties

/**
 * @author sokomishalov
 */
@Configuration
@EnableAsync
@EnableConfigurationProperties(MemeoryProperties::class)
@ConditionalOnProperty
class CommonConfig {

    @Bean
    @Primary
    fun objectMapper(): ObjectMapper = OBJECT_MAPPER

    @Bean
    @Primary
    fun reactiveWebClient(): WebClient = REACTIVE_WEB_CLIENT

    @Bean
    fun jackson2JsonDecoder(): Jackson2JsonDecoder = JACKSON_DECODER

    @Bean
    fun jackson2JsonEncoder(): Jackson2JsonEncoder = JACKSON_ENCODER

    @Bean
    @Qualifier("placeholder")
    fun placeholder(@Value("classpath:images/logo.png") logoPlaceHolder: Resource): ByteArray {
        return toByteArray(logoPlaceHolder.inputStream)
    }
}

