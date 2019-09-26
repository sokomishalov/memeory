package ru.sokomishalov.memeory.dto

import ru.sokomishalov.memeory.enums.PreferredOrientation
import ru.sokomishalov.memeory.enums.PreferredOrientation.VERTICAL


/**
 * @author sokomishalov
 */
data class ProfileDTO(
        var id: String? = null,
        var selectedOrientation: PreferredOrientation = VERTICAL,
        var watchAllChannels: Boolean = true,
        var channels: List<String>? = null,
        var socialsMap: Map<String, Map<String, Any?>> = emptyMap()
)
