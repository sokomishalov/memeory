package ru.sokomishalov.memeory.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.service.MemeService

@RestController
@RequestMapping("/memes")
class MemeController(private val memeService: MemeService) {

    @GetMapping("/all")
    fun all(): Flux<MemeDTO> {
        return memeService.findAllMemes()
    }
}
