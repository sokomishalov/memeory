package ru.sokomishalov.memeory.service.mongo

import org.springframework.stereotype.Service
import reactor.bool.not
import reactor.core.publisher.Flux
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.entity.Meme
import ru.sokomishalov.memeory.mapper.MemeMapper
import ru.sokomishalov.memeory.repository.MemeRepository
import ru.sokomishalov.memeory.service.MemeService

@Service
class MongoMemeService(
        private val repository: MemeRepository
) : MemeService {

    override fun saveMemesIfNotExist(memes: Flux<MemeDTO>): Flux<MemeDTO> {
        val memesToSaveFlux: Flux<Meme> = memes
                .filterWhen { !repository.existsById(it.id) }
                .map { MemeMapper.INSTANCE.toEntity(it) }

        return repository
                .saveAll(memesToSaveFlux)
                .map { MemeMapper.INSTANCE.toDto(it) }
    }

    override fun findAllMemes(): Flux<MemeDTO> {
        return repository
                .findAll()
                .map { MemeMapper.INSTANCE.toDto(it) }
    }

}