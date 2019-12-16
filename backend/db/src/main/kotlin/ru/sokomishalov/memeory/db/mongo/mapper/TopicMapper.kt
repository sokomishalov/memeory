package ru.sokomishalov.memeory.db.mongo.mapper

import org.mapstruct.Mapper
import org.springframework.stereotype.Component
import ru.sokomishalov.memeory.core.dto.TopicDTO
import ru.sokomishalov.memeory.db.mongo.entity.Topic

@Mapper(componentModel = "spring")
@Component
interface TopicMapper : AbstractMapper<Topic, TopicDTO>