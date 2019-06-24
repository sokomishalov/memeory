package ru.sokomishalov.memeory.service.api.reddit.model

import com.fasterxml.jackson.annotation.JsonProperty

class AboutData {

    @JsonProperty("icon_img")
    @get:JsonProperty("icon_img")
    @set:JsonProperty("icon_img")
    var iconImg: String? = null
    @JsonProperty("community_icon")
    @get:JsonProperty("community_icon")
    @set:JsonProperty("community_icon")
    var communityIcon: String? = null

}
