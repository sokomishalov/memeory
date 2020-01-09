package ru.sokomishalov.memeory.telegram.dto

import ru.sokomishalov.memeory.telegram.enum.BotScreen

/**
 * @author sokomishalov
 */
data class BotCallbackQueryDTO(
        val screen: BotScreen? = null,
        val id: String? = null
)