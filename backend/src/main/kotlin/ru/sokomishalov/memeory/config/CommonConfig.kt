package ru.sokomishalov.memeory.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import org.apache.commons.io.IOUtils.toByteArray
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.io.Resource
import org.springframework.scheduling.annotation.EnableAsync
import ru.sokomishalov.memeory.util.GsonHelper
import ru.sokomishalov.memeory.util.ObjectMapperHelper

/**
 * @author sokomishalov
 */
@Configuration
@EnableAsync
class CommonConfig {

    @Bean
    @Primary
    fun objectMapper(): ObjectMapper = ObjectMapperHelper.objectMapper

    @Bean
    @Primary
    fun gson(): Gson = GsonHelper.gson

    @Bean
    @Qualifier("placeholder")
    fun placeholder(@Value("classpath:images/logo.png") logoPlaceHolder: Resource): ByteArray {
        return toByteArray(logoPlaceHolder.inputStream)
    }
}

