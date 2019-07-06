package ru.sokomishalov.memeory.web

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import ru.sokomishalov.memeory.dto.ProfileDTO
import ru.sokomishalov.memeory.service.db.ProfileService


/**
 * @author sokomishalov
 */
@RestController
@RequestMapping("/profile")
class ProfileController(
        private val service: ProfileService
) {

    @PostMapping("/save")
    fun saveProfileInfo(@RequestBody profile: ProfileDTO): Mono<ProfileDTO> {
        return service.saveProfileInfo(profile)
    }

}
