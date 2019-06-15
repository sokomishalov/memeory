package ru.sokomishalov.memeory.service.api.reddit.model

import com.fasterxml.jackson.annotation.JsonProperty

class Child {

    @JsonProperty("kind")
    @get:JsonProperty("kind")
    @set:JsonProperty("kind")
    var kind: String? = null
    @JsonProperty("data")
    @get:JsonProperty("data")
    @set:JsonProperty("data")
    var data: DataInner? = null

}
