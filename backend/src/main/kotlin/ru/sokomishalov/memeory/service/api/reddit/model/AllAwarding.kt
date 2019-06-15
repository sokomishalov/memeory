package ru.sokomishalov.memeory.service.api.reddit.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

class AllAwarding {

    @JsonProperty("is_enabled")
    @get:JsonProperty("is_enabled")
    @set:JsonProperty("is_enabled")
    var isEnabled: Boolean? = null
    @JsonProperty("count")
    @get:JsonProperty("count")
    @set:JsonProperty("count")
    var count: Int? = null
    @JsonProperty("subreddit_id")
    @get:JsonProperty("subreddit_id")
    @set:JsonProperty("subreddit_id")
    var subredditId: Any? = null
    @JsonProperty("description")
    @get:JsonProperty("description")
    @set:JsonProperty("description")
    var description: String? = null
    @JsonProperty("coin_reward")
    @get:JsonProperty("coin_reward")
    @set:JsonProperty("coin_reward")
    var coinReward: Int? = null
    @JsonProperty("icon_width")
    @get:JsonProperty("icon_width")
    @set:JsonProperty("icon_width")
    var iconWidth: Int? = null
    @JsonProperty("icon_url")
    @get:JsonProperty("icon_url")
    @set:JsonProperty("icon_url")
    var iconUrl: String? = null
    @JsonProperty("days_of_premium")
    @get:JsonProperty("days_of_premium")
    @set:JsonProperty("days_of_premium")
    var daysOfPremium: Int? = null
    @JsonProperty("icon_height")
    @get:JsonProperty("icon_height")
    @set:JsonProperty("icon_height")
    var iconHeight: Int? = null
    @JsonProperty("resized_icons")
    @get:JsonProperty("resized_icons")
    @set:JsonProperty("resized_icons")
    var resizedIcons: List<ResizedIcon> = ArrayList()
    @JsonProperty("days_of_drip_extension")
    @get:JsonProperty("days_of_drip_extension")
    @set:JsonProperty("days_of_drip_extension")
    var daysOfDripExtension: Int? = null
    @JsonProperty("award_type")
    @get:JsonProperty("award_type")
    @set:JsonProperty("award_type")
    var awardType: String? = null
    @JsonProperty("coin_price")
    @get:JsonProperty("coin_price")
    @set:JsonProperty("coin_price")
    var coinPrice: Int? = null
    @JsonProperty("id")
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    var id: String? = null
    @JsonProperty("name")
    @get:JsonProperty("name")
    @set:JsonProperty("name")
    var name: String? = null

}
