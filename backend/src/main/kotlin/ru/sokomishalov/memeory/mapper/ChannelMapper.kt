package ru.sokomishalov.memeory.mapper

import org.mapstruct.Mapper
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.service.db.mongo.entity.Channel
import org.mapstruct.factory.Mappers.getMapper as generateMapper

@Mapper
interface ChannelMapper {
    companion object {
        val INSTANCE: ChannelMapper = generateMapper(ChannelMapper::class.java)
    }

    fun toEntity(dto: ChannelDTO): Channel

    fun toDto(entity: Channel): ChannelDTO
}
