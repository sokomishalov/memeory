package ru.sokomishalov.memeory.mapper

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import ru.sokomishalov.memeory.dto.MemeDTO
import ru.sokomishalov.memeory.entity.Meme

@Mapper
interface MemeMapper {
    companion object {
        val INSTANCE: MemeMapper = Mappers.getMapper(MemeMapper::class.java)
    }

    fun toEntity(dto: MemeDTO): Meme

    fun toDto(entity: Meme): MemeDTO
}
