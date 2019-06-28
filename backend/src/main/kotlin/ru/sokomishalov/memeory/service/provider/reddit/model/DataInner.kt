package ru.sokomishalov.memeory.service.provider.reddit.model

import com.fasterxml.jackson.annotation.JsonProperty

class DataInner {

    @JsonProperty("id")
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    var id: String? = null
    @JsonProperty("title")
    @get:JsonProperty("title")
    @set:JsonProperty("title")
    var title: String? = null
    @JsonProperty("preview")
    @get:JsonProperty("preview")
    @set:JsonProperty("preview")
    var preview: Preview? = null
    @JsonProperty("url")
    @get:JsonProperty("url")
    @set:JsonProperty("url")
    var url: String? = null
    @JsonProperty("created_utc")
    @get:JsonProperty("created_utc")
    @set:JsonProperty("created_utc")
    var createdUtc: Double? = null
    @JsonProperty("media")
    @get:JsonProperty("media")
    @set:JsonProperty("media")
    var media: Media? = null
}
