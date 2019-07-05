package ru.sokomishalov.memeory.dto

import ru.sokomishalov.memeory.util.VERTICAL_ORIENTATION


/**
 * @author sokomishalov
 */
data class ProfileDTO(
        var usesDarkTheme: Boolean = true,
        var selectedOrientation: String = VERTICAL_ORIENTATION,
        var watchAllChannels: Boolean = true,
        var channels: List<String> = emptyList(),
        var socialsMap: Map<String, Any> = emptyMap()
)
