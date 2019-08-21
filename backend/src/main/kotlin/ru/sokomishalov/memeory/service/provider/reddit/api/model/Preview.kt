package ru.sokomishalov.memeory.service.provider.reddit.api.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("images", "enabled")
class Preview {

    @JsonProperty("images")
    @get:JsonProperty("images")
    @set:JsonProperty("images")
    var images: List<Image> = ArrayList()
    @JsonProperty("enabled")
    @get:JsonProperty("enabled")
    @set:JsonProperty("enabled")
    var enabled: Boolean? = null

}
