package dto

data class Attachment(
        var url: String,
        var type: String = "NONE",
        var aspectRatio: Double? = null
)