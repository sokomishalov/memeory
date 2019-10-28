package ru.sokomishalov.memeory.db.mongo.mapper

import org.mapstruct.Mapper
import org.springframework.stereotype.Component
import ru.sokomishalov.memeory.core.dto.MemeDTO
import ru.sokomishalov.memeory.db.mongo.entity.Meme

@Mapper(componentModel = "spring")
@Component
interface MemeMapper : AbstractMapper<Meme, MemeDTO>
