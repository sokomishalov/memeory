package ru.sokomishalov.memeory.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import org.apache.commons.io.IOUtils.toByteArray
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.io.Resource
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.web.reactive.function.client.WebClient
import ru.sokomishalov.memeory.util.GSON
import ru.sokomishalov.memeory.util.OBJECT_MAPPER
import reactor.netty.http.client.HttpClient.create as createHttpClient

/**
 * @author sokomishalov
 */
@Configuration
@EnableAsync
@EnableConfigurationProperties(MemeoryProperties::class)
class CommonConfig {

    @Bean
    @Primary
    fun objectMapper(): ObjectMapper = OBJECT_MAPPER

    @Bean
    @Primary
    fun gson(): Gson = GSON

    @Bean
    @Qualifier("placeholder")
    fun placeholder(@Value("classpath:images/logo.png") logoPlaceHolder: Resource): ByteArray {
        return toByteArray(logoPlaceHolder.inputStream)
    }

    @Bean
    @Primary
    fun reactiveWebClient(): WebClient = WebClient
            .builder()
            .clientConnector(ReactorClientHttpConnector(createHttpClient().followRedirect(true)))
            .build()
}

