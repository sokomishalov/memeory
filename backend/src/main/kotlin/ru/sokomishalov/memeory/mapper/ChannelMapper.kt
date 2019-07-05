package ru.sokomishalov.memeory.mapper

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.entity.mongo.Channel

@Mapper
interface ChannelMapper {
    companion object {
        val INSTANCE: ChannelMapper = Mappers.getMapper(ChannelMapper::class.java)
    }

    fun toEntity(dto: ChannelDTO): Channel

    fun toDto(entity: Channel): ChannelDTO
}
