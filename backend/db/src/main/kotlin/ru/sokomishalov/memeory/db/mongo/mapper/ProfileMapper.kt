package ru.sokomishalov.memeory.db.mongo.mapper

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers.getMapper
import ru.sokomishalov.memeory.core.dto.ProfileDTO
import ru.sokomishalov.memeory.db.mongo.entity.Profile


@Mapper
interface ProfileMapper {
    companion object {
        val INSTANCE: ProfileMapper = getMapper(ProfileMapper::class.java)
    }

    fun toEntity(dto: ProfileDTO): Profile

    fun toDto(entity: Profile): ProfileDTO
}
