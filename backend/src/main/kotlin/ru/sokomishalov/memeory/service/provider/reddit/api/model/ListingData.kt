package ru.sokomishalov.memeory.service.provider.reddit.api.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

class ListingData {
    @JsonProperty("children")
    @get:JsonProperty("children")
    @set:JsonProperty("children")
    var children: List<Child> = ArrayList()
}
