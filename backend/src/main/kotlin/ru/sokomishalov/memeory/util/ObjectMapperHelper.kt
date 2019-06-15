package ru.sokomishalov.memeory.util

import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.DeserializationFeature.*
import com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS
import com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule

/**
 * @author sokomishalov
 */
object ObjectMapperHelper {
    val objectMapper: ObjectMapper = buildComplexObjectMapper()

    val yamlObjectMapper: ObjectMapper = buildComplexObjectMapper(YAMLFactory())

    private fun buildComplexObjectMapper(factory: JsonFactory? = null): ObjectMapper =
            ObjectMapper(factory)
                    .registerModule(JavaTimeModule())
                    .registerModule(KotlinModule())
                    .enable(
                            ACCEPT_SINGLE_VALUE_AS_ARRAY,
                            READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE,
                            ACCEPT_EMPTY_STRING_AS_NULL_OBJECT
                    )
                    .enable(
                            ACCEPT_CASE_INSENSITIVE_ENUMS
                    )
                    .disable(
                            FAIL_ON_EMPTY_BEANS,
                            WRITE_DATES_AS_TIMESTAMPS
                    )
                    .disable(
                            ADJUST_DATES_TO_CONTEXT_TIME_ZONE,
                            FAIL_ON_UNKNOWN_PROPERTIES
                    )
                    .setSerializationInclusion(NON_NULL)
}
