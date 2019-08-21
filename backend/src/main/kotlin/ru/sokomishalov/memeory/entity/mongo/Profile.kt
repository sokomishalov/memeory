package ru.sokomishalov.memeory.entity.mongo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ru.sokomishalov.memeory.util.consts.VERTICAL_ORIENTATION

@Document
data class Profile(
        @Id
        var id: String? = null,
        var selectedOrientation: String = VERTICAL_ORIENTATION,
        var watchAllChannels: Boolean = true,
        var channels: List<String>? = null,
        var socialsMap: Map<String, Map<String, Any>> = emptyMap()
)
