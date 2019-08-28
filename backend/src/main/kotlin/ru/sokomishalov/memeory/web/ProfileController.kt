package ru.sokomishalov.memeory.web

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sokomishalov.memeory.dto.ProfileDTO
import ru.sokomishalov.memeory.service.db.ProfileService
import ru.sokomishalov.memeory.util.extensions.await


/**
 * @author sokomishalov
 */
@RestController
@RequestMapping("/profile")
class ProfileController(
        private val service: ProfileService
) {

    @PostMapping("/save")
    suspend fun saveProfileInfo(@RequestBody profile: ProfileDTO): ProfileDTO? =
            service.saveIfNecessary(profile).await()
}
