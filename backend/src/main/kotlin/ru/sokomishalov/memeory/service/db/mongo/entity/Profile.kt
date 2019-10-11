package ru.sokomishalov.memeory.service.db.mongo.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ru.sokomishalov.memeory.enums.PreferredOrientation
import ru.sokomishalov.memeory.enums.PreferredOrientation.VERTICAL

@Document
data class Profile(
        @Id
        var id: String? = null,
        var selectedOrientation: PreferredOrientation = VERTICAL,
        var watchAllChannels: Boolean = true,
        var channels: List<String> = emptyList(),
        var socialsMap: Map<String, SocialAccount> = emptyMap()
)
