package ru.sokomishalov.memeory.core.dto

import ru.sokomishalov.memeory.core.enums.Provider

/**
 * @author sokomishalov
 */
data class BotUserDTO(
        var username: String = "",
        var fullName: String = "",
        var languageCode: String? = null,
        var chatId: Long = 0,
        val topics: List<String> = mutableListOf(),
        val channels: List<String> = mutableListOf(),
        val providers: List<Provider> = mutableListOf()
)