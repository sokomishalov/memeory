package ru.sokomishalov.memeory.web

import org.springframework.web.bind.annotation.*
import ru.sokomishalov.memeory.dto.ProfileDTO
import ru.sokomishalov.memeory.service.db.ProfileService
import ru.sokomishalov.memeory.util.consts.MEMEORY_TOKEN_HEADER


/**
 * @author sokomishalov
 */
@RestController
@RequestMapping("/profile")
class ProfileController(
        private val service: ProfileService
) {

    @GetMapping("/get")
    suspend fun getProfile(@RequestHeader(name = MEMEORY_TOKEN_HEADER) token: String): ProfileDTO? =
            service.findById(token)

    @PostMapping("/save")
    suspend fun saveProfileInfo(@RequestBody profile: ProfileDTO): ProfileDTO? =
            service.saveIfNecessary(profile)
}
