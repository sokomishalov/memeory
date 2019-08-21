package ru.sokomishalov.memeory.service.provider.reddit.api.model

import com.fasterxml.jackson.annotation.JsonProperty

class Source {

    @JsonProperty("url")
    @get:JsonProperty("url")
    @set:JsonProperty("url")
    var url: String? = null
    @JsonProperty("width")
    @get:JsonProperty("width")
    @set:JsonProperty("width")
    var width: Int? = null
    @JsonProperty("height")
    @get:JsonProperty("height")
    @set:JsonProperty("height")
    var height: Int? = null

}
