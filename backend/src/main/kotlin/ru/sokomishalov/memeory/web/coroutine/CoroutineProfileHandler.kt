package ru.sokomishalov.memeory.web.coroutine

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import ru.sokomishalov.memeory.dto.ProfileDTO
import ru.sokomishalov.memeory.service.db.ProfileService
import ru.sokomishalov.memeory.util.extensions.awaitResponse


/**
 * @author sokomishalov
 */
@Component
class CoroutineProfileHandler(
        private val service: ProfileService
) {

    suspend fun saveProfile(request: ServerRequest): ServerResponse {
        val profile = request.awaitBody<ProfileDTO>()
        return service.saveIfNecessary(profile).awaitResponse()
    }
}
