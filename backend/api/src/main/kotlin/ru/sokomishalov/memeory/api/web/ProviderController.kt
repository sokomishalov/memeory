package ru.sokomishalov.memeory.api.web

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpHeaders.ACCESS_CONTROL_MAX_AGE
import org.springframework.http.HttpHeaders.CONTENT_DISPOSITION
import org.springframework.http.MediaType.IMAGE_PNG
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sokomishalov.memeory.core.dto.ProviderDTO
import ru.sokomishalov.memeory.core.enums.Provider
import ru.sokomishalov.memeory.core.util.consts.DELIMITER

/**
 * @author sokomishalov
 */
@RestController
@RequestMapping("/providers")
class ProviderController(
        @Qualifier("placeholder")
        private val placeholder: ByteArray
) {

    @GetMapping("/list")
    suspend fun list(): List<ProviderDTO> {
        return Provider
                .values()
                .map { ProviderDTO(providerId = it, logoUri = "providers/logo/${it.name}") }
    }

    @GetMapping("/logo/{name}")
    suspend fun logo(@PathVariable("name") name: String): ResponseEntity<ByteArray> {
        return ResponseEntity
                .ok()
                .contentType(IMAGE_PNG)
                .contentLength(placeholder.size.toLong())
                .header(CONTENT_DISPOSITION, "attachment; filename=${name}${DELIMITER}logo.png")
                .header(ACCESS_CONTROL_MAX_AGE, "31536000", "public")
                .body(placeholder)
    }

}