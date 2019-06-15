package ru.sokomishalov.memeory.service

import reactor.core.publisher.Flux
import ru.sokomishalov.memeory.dto.MemeDTO

interface MemeService {

    fun saveMemesIfNotExist(memes: Flux<MemeDTO>): Flux<MemeDTO>

    fun findAllMemes(): Flux<MemeDTO>

}