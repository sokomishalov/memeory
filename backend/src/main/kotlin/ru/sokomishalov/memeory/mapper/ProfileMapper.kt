package ru.sokomishalov.memeory.mapper

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import ru.sokomishalov.memeory.dto.ProfileDTO
import ru.sokomishalov.memeory.entity.mongo.Profile


@Mapper
interface ProfileMapper {
    companion object {
        val INSTANCE: ProfileMapper = Mappers.getMapper(ProfileMapper::class.java)
    }

    fun toEntity(dto: ProfileDTO): Profile

    fun toDto(entity: Profile): ProfileDTO
}