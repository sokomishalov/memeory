package ru.sokomishalov.memeory.dto

import ru.sokomishalov.memeory.enums.ScrollingAxis
import ru.sokomishalov.memeory.enums.ScrollingAxis.VERTICAL


/**
 * @author sokomishalov
 */
data class ProfileDTO(
        var id: String? = null,
        var selectedOrientation: ScrollingAxis = VERTICAL,
        var watchAllChannels: Boolean = true,
        var channels: List<String> = emptyList(),
        var socialsMap: Map<String, SocialAccountDTO> = emptyMap()
)
