package ru.sokomishalov.memeory.service

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.sokomishalov.memeory.dto.SourceDTO

interface SourceService {

    fun saveOne(source: SourceDTO): Mono<SourceDTO>

    fun saveBatchIfNotExist(batch: List<SourceDTO>): Flux<SourceDTO>

    fun listSources(): Flux<SourceDTO>
}
