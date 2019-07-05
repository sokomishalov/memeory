package ru.sokomishalov.memeory.web

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import ru.sokomishalov.memeory.dto.ProfileDTO


/**
 * @author sokomishalov
 */
@RestController
@RequestMapping("profile")
class ProfileController {

    @PostMapping
    fun saveProfileInfo(@RequestBody profile: ProfileDTO): Mono<Void> {
        return Mono.empty()
    }

}
