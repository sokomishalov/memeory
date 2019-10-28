package ru.sokomishalov.memeory.core.dto

import ru.sokomishalov.commons.core.consts.EMPTY

/**
 * @author sokomishalov
 */
data class TelegramAccountDTO(
        val id: Long = 0,
        val fullName: String = EMPTY,
        val username: String = EMPTY,
        val languageCode: String? = null,
        val chatId: Long = 0
)