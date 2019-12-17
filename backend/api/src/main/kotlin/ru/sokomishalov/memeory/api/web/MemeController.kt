package ru.sokomishalov.memeory.api.web

import org.springframework.web.bind.annotation.*
import ru.sokomishalov.memeory.api.dto.MemesPageRequestDTO
import ru.sokomishalov.memeory.core.dto.MemeDTO
import ru.sokomishalov.memeory.db.MemeService

@RestController
@RequestMapping("/memes")
class MemeController(
        private val service: MemeService
) {

    @PostMapping("/page")
    suspend fun page(@RequestBody request: MemesPageRequestDTO): List<MemeDTO> {
        return service.getPage(request.pageNumber, request.pageSize, request.topic)
    }

    @GetMapping("/one/{id}")
    suspend fun getOne(@PathVariable("id") id: String): MemeDTO? {
        return service.findById(id)
    }
}
