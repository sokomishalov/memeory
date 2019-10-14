package ru.sokomishalov.memeory.service.db.mongo.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ru.sokomishalov.memeory.enums.ScrollingAxis
import ru.sokomishalov.memeory.enums.ScrollingAxis.VERTICAL

@Document
data class Profile(
        @Id
        var id: String? = null,
        var selectedOrientation: ScrollingAxis = VERTICAL,
        var watchAllChannels: Boolean = true,
        var channels: List<String> = emptyList(),
        var socialsMap: Map<String, SocialAccount> = emptyMap()
)
