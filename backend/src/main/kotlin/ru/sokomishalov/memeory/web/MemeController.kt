package ru.sokomishalov.memeory.web

import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import ru.sokomishalov.memeory.condition.ConditionalOnNotUsingCoroutines
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.service.db.MemeService
import ru.sokomishalov.memeory.util.consts.MEMEORY_TOKEN_HEADER

@RestController
@RequestMapping("/memes")
@ConditionalOnNotUsingCoroutines
class MemeController(
        private val service: MemeService
) {

    @GetMapping("/page/{page}/{count}")
    fun page(@PathVariable page: Int,
             @PathVariable count: Int,
             @RequestHeader(required = false, name = MEMEORY_TOKEN_HEADER) token: String?
    ): Flux<MemeDTO> {
        return service.pageOfMemes(page, count, token)
    }
}
