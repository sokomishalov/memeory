package ru.sokomishalov.memeory.db.mongo.mapper

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers.getMapper
import ru.sokomishalov.memeory.core.dto.ChannelDTO
import ru.sokomishalov.memeory.db.mongo.entity.Channel

@Mapper
interface ChannelMapper {
    companion object {
        val INSTANCE: ChannelMapper = getMapper(ChannelMapper::class.java)
    }

    fun toEntity(dto: ChannelDTO): Channel

    fun toDto(entity: Channel): ChannelDTO
}
