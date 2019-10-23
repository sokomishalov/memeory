package ru.sokomishalov.memeory.dto

import ru.sokomishalov.commons.core.consts.EMPTY

/**
 * @author sokomishalov
 */
data class TopicDTO(
        var id: String = EMPTY,
        var caption: String = EMPTY
)