package ru.sokomishalov.memeory.api.dto

/**
 * @author sokomishalov
 */
data class MemesPageRequestDTO(
        val topic: String?,
        val pageSize: Int,
        val pageCount: Int
)