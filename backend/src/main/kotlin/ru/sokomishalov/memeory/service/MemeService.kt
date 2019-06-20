package ru.sokomishalov.memeory.service

import reactor.core.publisher.Flux
import ru.sokomishalov.memeory.dto.MemeDTO

interface MemeService {

    fun saveMemesIfNotExist(memes: Flux<MemeDTO>): Flux<MemeDTO>

    fun pageOfMemes(page: Int, count: Int): Flux<MemeDTO>
}
