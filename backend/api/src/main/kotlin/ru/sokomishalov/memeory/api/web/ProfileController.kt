package ru.sokomishalov.memeory.api.web

import org.springframework.web.bind.annotation.*
import ru.sokomishalov.memeory.core.dto.ProfileDTO
import ru.sokomishalov.memeory.core.dto.SocialAccountDTO
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

    @PostMapping("/update")
    suspend fun saveProfileInfo(@RequestBody profile: ProfileDTO): ProfileDTO? {
        return service.update(profile)
    }

    @PostMapping("/socials/add")
    suspend fun addSocialAccount(@RequestHeader(name = MEMEORY_TOKEN_HEADER, required = false) token: String?,
                                 @RequestBody account: SocialAccountDTO): ProfileDTO? {
        return service.saveSocialAccount(token, account)
    }
}
