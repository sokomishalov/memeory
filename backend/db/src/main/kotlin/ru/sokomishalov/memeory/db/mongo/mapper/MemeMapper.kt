package ru.sokomishalov.memeory.db.mongo.mapper

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers.getMapper
import ru.sokomishalov.memeory.core.dto.MemeDTO
import ru.sokomishalov.memeory.db.mongo.entity.Meme

@Mapper
interface MemeMapper {
    companion object {
        val INSTANCE: MemeMapper = getMapper(MemeMapper::class.java)
    }

    fun toEntity(dto: MemeDTO): Meme

    fun toDto(entity: Meme): MemeDTO
}
