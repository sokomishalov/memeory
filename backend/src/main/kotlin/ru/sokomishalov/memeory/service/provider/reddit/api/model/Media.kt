package ru.sokomishalov.memeory.service.provider.reddit.api.model

import com.fasterxml.jackson.annotation.JsonProperty

class Media {
    @JsonProperty("type")
    @get:JsonProperty("type")
    @set:JsonProperty("type")
    var type: String? = null
}
