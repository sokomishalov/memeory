package ru.sokomishalov.memeory.dto

import ru.sokomishalov.commons.core.consts.EMPTY
import ru.sokomishalov.memeory.enums.SourceType


/**
 * @author sokomishalov
 */
data class ChannelDTO(
        var id: String = EMPTY,
        var enabled: Boolean? = false,
        var sourceType: SourceType? = null,
        var name: String? = EMPTY,
        var uri: String = EMPTY
)

