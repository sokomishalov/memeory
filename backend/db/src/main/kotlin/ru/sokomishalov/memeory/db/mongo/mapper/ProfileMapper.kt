package ru.sokomishalov.memeory.db.mongo.mapper

import org.mapstruct.Mapper
import org.springframework.stereotype.Component
import ru.sokomishalov.memeory.core.dto.ProfileDTO
import ru.sokomishalov.memeory.db.mongo.entity.Profile

@Mapper(componentModel = "spring")
@Component
interface ProfileMapper : AbstractMapper<Profile, ProfileDTO>
