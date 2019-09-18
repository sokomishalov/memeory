package ru.sokomishalov.memeory.web

import kotlinx.coroutines.runBlocking
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.permanentRedirect
import ru.sokomishalov.commons.core.reactor.awaitStrict
import springfox.documentation.annotations.ApiIgnore
import java.net.URI.create as uriCreate

/**
 * @author sokomishalov
 */
@RestController
@ApiIgnore
class SwaggerIndexController {

    @RequestMapping("/")
    fun redirect(): ServerResponse = runBlocking { permanentRedirect(uriCreate("/swagger-ui.html")).build().awaitStrict() }

}