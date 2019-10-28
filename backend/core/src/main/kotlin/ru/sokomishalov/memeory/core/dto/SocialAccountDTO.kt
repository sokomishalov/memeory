package ru.sokomishalov.memeory.core.dto

/**
 * @author sokomishalov
 */
data class SocialAccountDTO(
        var providerId: String? = null,
        var uid: String? = null,
        var displayName: String? = null,
        var photoURL: String? = null,
        var email: String? = null,
        var phoneNumber: String? = null
)