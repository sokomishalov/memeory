package ru.sokomishalov.memeory.web

import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.service.db.MemeService
import ru.sokomishalov.memeory.util.MEMEORY_TOKEN_HEADER

@RestController
@RequestMapping("/memes")
class MemeController(private val memeService: MemeService) {

    @GetMapping("/page/{page}/{count}")
    fun page(@PathVariable page: Int,
             @PathVariable count: Int,
             @RequestHeader(required = false, name = MEMEORY_TOKEN_HEADER) token: String?
    ): Flux<MemeDTO> {
        return memeService.pageOfMemes(page, count, token)
    }
}
