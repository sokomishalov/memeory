package ru.sokomishalov.memeory.web

import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.sokomishalov.memeory.dto.SourceDTO
import ru.sokomishalov.memeory.service.SourceService


/**
 * @author sokomishalov
 */
@RestController
@RequestMapping("/sources")
class AccountController(private val serviceMongo: SourceService) {

    @GetMapping("/list")
    fun all(): Flux<SourceDTO> {
        return serviceMongo.listSources()
    }

    @PostMapping("/add")
    fun add(@RequestBody account: SourceDTO): Mono<SourceDTO> {
        return serviceMongo.saveOne(account)
    }
}
