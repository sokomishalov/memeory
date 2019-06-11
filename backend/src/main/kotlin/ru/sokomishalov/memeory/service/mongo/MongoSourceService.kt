package ru.sokomishalov.memeory.service.mongo

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.sokomishalov.memeory.dto.SourceDTO
import ru.sokomishalov.memeory.mapper.SourceMapper
import ru.sokomishalov.memeory.repository.SourceRepository
import ru.sokomishalov.memeory.service.SourceService

/**
 * @author sokomishalov
 */
@Service
class MongoSourceService(private val repository: SourceRepository) : SourceService {
    override fun saveOne(source: SourceDTO): Mono<SourceDTO> {
        return Mono
                .just(source)
                .map { SourceMapper.INSTANCE.toEntity(it) }
                .flatMap { repository.save(it) }
                .map { SourceMapper.INSTANCE.toDto(it) }

    }

    override fun saveBatchIfNotExist(batch: List<SourceDTO>): Flux<SourceDTO> {
        return Flux
                .fromIterable(batch)
                .filterWhen { repository.existsById(it.id).map(Boolean::not) }
                .map { SourceMapper.INSTANCE.toEntity(it) }
                .collectList()
                .flatMapMany { repository.saveAll(it) }
                .map { SourceMapper.INSTANCE.toDto(it) }
    }

    override fun listSources(): Flux<SourceDTO> {
        return repository
                .findAll()
                .map { SourceMapper.INSTANCE.toDto(it) }
    }
}
