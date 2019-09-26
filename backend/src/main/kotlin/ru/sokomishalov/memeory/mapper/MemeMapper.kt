package ru.sokomishalov.memeory.mapper

import org.mapstruct.Mapper
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.service.db.mongo.entity.Meme
import org.mapstruct.factory.Mappers.getMapper as generateMapper

@Mapper
interface MemeMapper {
    companion object {
        val INSTANCE: MemeMapper = generateMapper(MemeMapper::class.java)
    }

    fun toEntity(dto: MemeDTO): Meme

    fun toDto(entity: Meme): MemeDTO
}
