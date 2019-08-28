package ru.sokomishalov.memeory.web

import org.springframework.web.bind.annotation.*
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.service.db.MemeService
import ru.sokomishalov.memeory.util.consts.MEMEORY_TOKEN_HEADER
import ru.sokomishalov.memeory.util.extensions.await

@RestController
@RequestMapping("/memes")
class MemeController(
        private val service: MemeService
) {

    @GetMapping("/page/{page}/{count}")
    suspend fun page(@PathVariable page: Int,
                     @PathVariable count: Int,
                     @RequestHeader(required = false, name = MEMEORY_TOKEN_HEADER) token: String?
    ): List<MemeDTO> =
            service.pageOfMemes(page, count, token).await()
}
