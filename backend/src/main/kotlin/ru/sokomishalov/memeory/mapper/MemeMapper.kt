package ru.sokomishalov.memeory.mapper

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers.getMapper
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.service.db.mongo.entity.Meme

@Mapper
interface MemeMapper {
    companion object {
        val INSTANCE: MemeMapper = getMapper(MemeMapper::class.java)
    }

    fun toEntity(dto: MemeDTO): Meme

    fun toDto(entity: Meme): MemeDTO
}
