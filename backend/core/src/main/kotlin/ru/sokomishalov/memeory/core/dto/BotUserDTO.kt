package ru.sokomishalov.memeory.core.dto

import ru.sokomishalov.commons.core.consts.EMPTY

/**
 * @author sokomishalov
 */
data class BotUserDTO(
        var username: String = EMPTY,
        var fullName: String = EMPTY,
        var languageCode: String? = null,
        var chatId: Long = 0
)