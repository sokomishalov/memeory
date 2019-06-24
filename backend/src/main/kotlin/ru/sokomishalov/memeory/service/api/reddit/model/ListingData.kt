package ru.sokomishalov.memeory.service.api.reddit.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

class ListingData {

    @JsonProperty("modhash")
    @get:JsonProperty("modhash")
    @set:JsonProperty("modhash")
    var modhash: String? = null
    @JsonProperty("dist")
    @get:JsonProperty("dist")
    @set:JsonProperty("dist")
    var dist: Int? = null
    @JsonProperty("children")
    @get:JsonProperty("children")
    @set:JsonProperty("children")
    var children: List<Child> = ArrayList()
    @JsonProperty("after")
    @get:JsonProperty("after")
    @set:JsonProperty("after")
    var after: String? = null
    @JsonProperty("before")
    @get:JsonProperty("before")
    @set:JsonProperty("before")
    var before: Any? = null

}
