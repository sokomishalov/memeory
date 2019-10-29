package ru.sokomishalov.memeory.api.web

import org.springframework.web.bind.annotation.*
import ru.sokomishalov.memeory.core.dto.ProfileDTO
import ru.sokomishalov.memeory.core.util.consts.MEMEORY_TOKEN_HEADER
import ru.sokomishalov.memeory.db.ProfileService


/**
 * @author sokomishalov
 */
@RestController
@RequestMapping("/profile")
class ProfileController(
        private val service: ProfileService
) {

    @GetMapping("/get")
    suspend fun getProfile(@RequestHeader(name = MEMEORY_TOKEN_HEADER) token: String): ProfileDTO? {
        return service.findById(token)
    }

    @PostMapping("/save")
    suspend fun saveProfileInfo(@RequestBody profile: ProfileDTO): ProfileDTO? {
        return service.save(profile)
    }
}
