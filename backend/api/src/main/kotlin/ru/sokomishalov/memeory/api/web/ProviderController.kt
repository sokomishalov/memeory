package ru.sokomishalov.memeory.api.web

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.io.ResourceLoader
import org.springframework.http.HttpHeaders.ACCESS_CONTROL_MAX_AGE
import org.springframework.http.HttpHeaders.CONTENT_DISPOSITION
import org.springframework.http.MediaType.IMAGE_PNG
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sokomishalov.commons.core.io.toByteArray
import ru.sokomishalov.memeory.core.enums.Provider
import ru.sokomishalov.memeory.core.util.consts.DELIMITER

/**
 * @author sokomishalov
 */
@RestController
@RequestMapping("/providers")
class ProviderController(
        @Qualifier("placeholder")
        private val placeholder: ByteArray,
        private val resourceLoader: ResourceLoader
) {

    @GetMapping("/list")
    suspend fun list(): Array<Provider> {
        return Provider.values()
    }

    @GetMapping("/logo/{name}")
    suspend fun logo(@PathVariable("name") provider: Provider): ResponseEntity<ByteArray> {
        val logo = runCatching {
            resourceLoader
                    .getResource("classpath:providers/${provider.name.toLowerCase()}.png")
                    .inputStream
                    .use { it.toByteArray() }
        }.getOrElse {
            placeholder
        }
        return ResponseEntity
                .ok()
                .contentType(IMAGE_PNG)
                .contentLength(logo.size.toLong())
                .header(CONTENT_DISPOSITION, "attachment; filename=${provider.name.toLowerCase()}${DELIMITER}logo.png")
                .header(ACCESS_CONTROL_MAX_AGE, "31536000", "public")
                .body(logo)
    }

}