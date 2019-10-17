package ru.sokomishalov.memeory.service.db.mongo.entity

/**
 * @author sokomishalov
 */
data class SocialAccount(
        var uid: String? = null,
        var displayName: String? = null,
        var photoURL: String? = null,
        var email: String? = null,
        var phoneNumber: String? = null
)