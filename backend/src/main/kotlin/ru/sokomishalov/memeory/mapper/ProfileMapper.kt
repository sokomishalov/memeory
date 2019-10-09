package ru.sokomishalov.memeory.mapper

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers.getMapper
import ru.sokomishalov.memeory.dto.ProfileDTO
import ru.sokomishalov.memeory.service.db.mongo.entity.Profile


@Mapper
interface ProfileMapper {
    companion object {
        val INSTANCE: ProfileMapper = getMapper(ProfileMapper::class.java)
    }

    fun toEntity(dto: ProfileDTO): Profile

    fun toDto(entity: Profile): ProfileDTO
}
