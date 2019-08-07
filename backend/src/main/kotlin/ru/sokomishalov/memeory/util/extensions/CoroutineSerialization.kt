package ru.sokomishalov.memeory.util.extensions

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext


/**
 * @author sokomishalov
 */

suspend fun ObjectMapper.coReadTree(content: String): JsonNode = withContext(IO) {
    readTree(content)
}
