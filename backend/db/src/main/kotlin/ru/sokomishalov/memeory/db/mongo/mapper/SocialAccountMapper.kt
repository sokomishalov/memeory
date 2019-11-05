package ru.sokomishalov.memeory.db.mongo.mapper

import org.mapstruct.Mapper
import org.springframework.stereotype.Component
import ru.sokomishalov.memeory.core.dto.SocialAccountDTO
import ru.sokomishalov.memeory.db.mongo.entity.SocialAccount

@Mapper(componentModel = "spring")
@Component
interface SocialAccountMapper : AbstractMapper<SocialAccount, SocialAccountDTO>
