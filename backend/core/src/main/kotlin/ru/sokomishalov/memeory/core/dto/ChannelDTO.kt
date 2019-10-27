package ru.sokomishalov.memeory.core.dto

import ru.sokomishalov.commons.core.consts.EMPTY
import ru.sokomishalov.memeory.core.enums.Provider


/**
 * @author sokomishalov
 */
data class ChannelDTO(
        var id: String = EMPTY,
        var enabled: Boolean? = false,
        var provider: Provider? = null,
        var name: String? = EMPTY,
        var uri: String = EMPTY
)

