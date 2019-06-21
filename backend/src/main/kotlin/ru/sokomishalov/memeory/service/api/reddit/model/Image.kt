package ru.sokomishalov.memeory.service.api.reddit.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

class Image {

    @JsonProperty("source")
    @get:JsonProperty("source")
    @set:JsonProperty("source")
    var source: Source? = null
    @JsonProperty("resolutions")
    @get:JsonProperty("resolutions")
    @set:JsonProperty("resolutions")
    var resolutions: List<Resolution> = ArrayList()
    @JsonProperty("id")
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    var id: String? = null

}
