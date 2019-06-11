package ru.sokomishalov.memeory.mapper

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import ru.sokomishalov.memeory.dto.SourceDTO
import ru.sokomishalov.memeory.entity.Source

@Mapper
interface SourceMapper {
    companion object {
        val INSTANCE: SourceMapper = Mappers.getMapper(SourceMapper::class.java)
    }

    fun toEntity(dto: SourceDTO): Source

    fun toDto(entity: Source): SourceDTO
}
