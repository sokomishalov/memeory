package ru.sokomishalov.memeory.service.mongo

import org.springframework.stereotype.Service
import reactor.bool.not
import reactor.core.publisher.Flux
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.entity.Meme
import ru.sokomishalov.memeory.repository.MemeRepository
import ru.sokomishalov.memeory.service.MemeService
import ru.sokomishalov.memeory.mapper.MemeMapper.Companion.INSTANCE as memeMapper

@Service
class MongoMemeService(
        private val repository: MemeRepository
) : MemeService {

    override fun saveMemesIfNotExist(memes: Flux<MemeDTO>): Flux<MemeDTO> {
        val memesToSaveFlux: Flux<Meme> = memes
                .filterWhen { !repository.existsById(it.id) }
                .map { memeMapper.toEntity(it) }

        return repository
                .saveAll(memesToSaveFlux)
                .map { memeMapper.toDto(it) }
    }

    override fun findAllMemes(): Flux<MemeDTO> {
        return repository
                .findAll()
                .map { memeMapper.toDto(it) }
                .sort { o1, o2 -> o2.publishedAt.compareTo(o1.publishedAt) }
    }

}
