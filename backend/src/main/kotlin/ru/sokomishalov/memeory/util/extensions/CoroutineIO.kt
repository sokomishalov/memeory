@file:Suppress("unused")

package ru.sokomishalov.memeory.util.extensions

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import org.springframework.core.ResolvableType.NONE
import org.springframework.core.codec.ByteArrayDecoder
import org.springframework.http.codec.multipart.FilePart


/**
 * @author sokomishalov
 */

suspend fun ObjectMapper.aReadTree(content: String): JsonNode = withContext(IO) {
    readTree(content)
}

suspend inline fun <reified T> ObjectMapper.aReadValue(content: String): T = withContext(IO) {
    readValue(content)
}

suspend inline fun <reified T> ObjectMapper.aConvertValue(from: String): T = withContext(IO) {
    convertValue(from)
}

suspend fun FilePart.convertToByteArray(): ByteArray = withContext(IO) {
    ByteArrayDecoder()
            .decodeToMono(content(), NONE, null, emptyMap<String, Any>())
            .awaitStrict()
}