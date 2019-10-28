package ru.sokomishalov.memeory.db.mongo.mapper

import org.mapstruct.Mapper
import org.springframework.stereotype.Component
import ru.sokomishalov.memeory.core.dto.BotUserDTO
import ru.sokomishalov.memeory.db.mongo.entity.BotUser

@Mapper(componentModel = "spring")
@Component
interface BotUserMapper : AbstractMapper<BotUser, BotUserDTO>