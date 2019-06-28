package ru.sokomishalov.memeory.service.db.mongo

import org.springframework.data.domain.PageRequest.of
import org.springframework.data.domain.Sort.Direction.DESC
import org.springframework.data.domain.Sort.NullHandling.NULLS_LAST
import org.springframework.data.domain.Sort.Order
import org.springframework.data.domain.Sort.by
import org.springframework.stereotype.Service
import reactor.bool.not
import reactor.core.publisher.Flux
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.repository.MemeRepository
import ru.sokomishalov.memeory.service.db.MemeService
import ru.sokomishalov.memeory.mapper.MemeMapper.Companion.INSTANCE as memeMapper

@Service
class MongoMemeService(
        private val repository: MemeRepository
) : MemeService {

    override fun saveMemesIfNotExist(memes: Flux<MemeDTO>): Flux<MemeDTO> {
        return memes
                .filterWhen { repository.existsById(it.id).not() }
                .map { memeMapper.toEntity(it) }
                .let { repository.saveAll(it) }
                .map { memeMapper.toDto(it) }

    }

    override fun pageOfMemes(page: Int, count: Int): Flux<MemeDTO> {
        return repository
                .findMemeBy(of(page, count, by(Order(DESC, "publishedAt", NULLS_LAST))))
                .map { memeMapper.toDto(it) }
    }
}
