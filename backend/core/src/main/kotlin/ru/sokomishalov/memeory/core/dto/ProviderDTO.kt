package ru.sokomishalov.memeory.core.dto

import ru.sokomishalov.memeory.core.enums.Provider

/**
 * @author sokomishalov
 */
data class ProviderDTO(
        val providerId: Provider,
        val logoUri: String
)