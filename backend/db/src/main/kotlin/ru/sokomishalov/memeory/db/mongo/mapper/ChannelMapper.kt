package ru.sokomishalov.memeory.db.mongo.mapper

import org.mapstruct.Mapper
import org.springframework.stereotype.Component
import ru.sokomishalov.memeory.core.dto.ChannelDTO
import ru.sokomishalov.memeory.db.mongo.entity.Channel

@Mapper(componentModel = "spring")
@Component
interface ChannelMapper : AbstractMapper<Channel, ChannelDTO>
