package ru.sokomishalov.memeory.telegram.dto

import ru.sokomishalov.memeory.telegram.enum.FilterType

/**
 * @author sokomishalov
 */
data class BotCallbackQueryDTO(
        val filterType: FilterType? = null,
        val id: String? = null
)