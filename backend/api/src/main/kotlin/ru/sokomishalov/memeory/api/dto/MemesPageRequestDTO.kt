package ru.sokomishalov.memeory.api.dto

/**
 * @author sokomishalov
 */
data class MemesPageRequestDTO(
        val topic: String?,
        val channel: String?,
        val pageNumber: Int,
        val pageSize: Int
)