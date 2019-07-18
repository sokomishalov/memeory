package ru.sokomishalov.memeory.dto

import ru.sokomishalov.memeory.util.VERTICAL_ORIENTATION


/**
 * @author sokomishalov
 */
data class ProfileDTO(
        var id: String? = null,
        var selectedOrientation: String = VERTICAL_ORIENTATION,
        var watchAllChannels: Boolean = true,
        var channels: List<String>? = null,
        var socialsMap: Map<String, Map<String, Any>> = emptyMap()
)
