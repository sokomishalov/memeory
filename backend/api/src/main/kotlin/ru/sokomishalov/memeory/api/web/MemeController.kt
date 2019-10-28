package ru.sokomishalov.memeory.api.web

import org.springframework.web.bind.annotation.*
import ru.sokomishalov.memeory.core.dto.MemeDTO
import ru.sokomishalov.memeory.core.util.consts.MEMEORY_TOKEN_HEADER
import ru.sokomishalov.memeory.db.MemeService

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
            service.getPage(page, count, token)

    @GetMapping("/one/{id}")
    suspend fun getOne(@PathVariable id: String): MemeDTO? =
            service.findById(id)

}
