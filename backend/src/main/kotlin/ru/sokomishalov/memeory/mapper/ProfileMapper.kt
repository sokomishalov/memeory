package ru.sokomishalov.memeory.mapper

import org.mapstruct.Mapper
import ru.sokomishalov.memeory.dto.ProfileDTO
import ru.sokomishalov.memeory.entity.mongo.Profile
import org.mapstruct.factory.Mappers.getMapper as generateMapper


@Mapper
interface ProfileMapper {
    companion object {
        val INSTANCE: ProfileMapper = generateMapper(ProfileMapper::class.java)
    }

    fun toEntity(dto: ProfileDTO): Profile

    fun toDto(entity: Profile): ProfileDTO
}
