package ru.sokomishalov.memeory.service.api.reddit.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

class DataInner {

    @JsonProperty("approved_at_utc")
    @get:JsonProperty("approved_at_utc")
    @set:JsonProperty("approved_at_utc")
    var approvedAtUtc: Any? = null
    @JsonProperty("subreddit")
    @get:JsonProperty("subreddit")
    @set:JsonProperty("subreddit")
    var subreddit: String? = null
    @JsonProperty("selftext")
    @get:JsonProperty("selftext")
    @set:JsonProperty("selftext")
    var selftext: String? = null
    @JsonProperty("author_fullname")
    @get:JsonProperty("author_fullname")
    @set:JsonProperty("author_fullname")
    var authorFullname: String? = null
    @JsonProperty("saved")
    @get:JsonProperty("saved")
    @set:JsonProperty("saved")
    var saved: Boolean? = null
    @JsonProperty("mod_reason_title")
    @get:JsonProperty("mod_reason_title")
    @set:JsonProperty("mod_reason_title")
    var modReasonTitle: Any? = null
    @JsonProperty("gilded")
    @get:JsonProperty("gilded")
    @set:JsonProperty("gilded")
    var gilded: Int? = null
    @JsonProperty("clicked")
    @get:JsonProperty("clicked")
    @set:JsonProperty("clicked")
    var clicked: Boolean? = null
    @JsonProperty("title")
    @get:JsonProperty("title")
    @set:JsonProperty("title")
    var title: String? = null
    @JsonProperty("link_flair_richtext")
    @get:JsonProperty("link_flair_richtext")
    @set:JsonProperty("link_flair_richtext")
    var linkFlairRichtext: List<Any> = ArrayList()
    @JsonProperty("subreddit_name_prefixed")
    @get:JsonProperty("subreddit_name_prefixed")
    @set:JsonProperty("subreddit_name_prefixed")
    var subredditNamePrefixed: String? = null
    @JsonProperty("hidden")
    @get:JsonProperty("hidden")
    @set:JsonProperty("hidden")
    var hidden: Boolean? = null
    @JsonProperty("pwls")
    @get:JsonProperty("pwls")
    @set:JsonProperty("pwls")
    var pwls: Int? = null
    @JsonProperty("link_flair_css_class")
    @get:JsonProperty("link_flair_css_class")
    @set:JsonProperty("link_flair_css_class")
    var linkFlairCssClass: Any? = null
    @JsonProperty("downs")
    @get:JsonProperty("downs")
    @set:JsonProperty("downs")
    var downs: Int? = null
    @JsonProperty("thumbnail_height")
    @get:JsonProperty("thumbnail_height")
    @set:JsonProperty("thumbnail_height")
    var thumbnailHeight: Int? = null
    @JsonProperty("hide_score")
    @get:JsonProperty("hide_score")
    @set:JsonProperty("hide_score")
    var hideScore: Boolean? = null
    @JsonProperty("name")
    @get:JsonProperty("name")
    @set:JsonProperty("name")
    var name: String? = null
    @JsonProperty("quarantine")
    @get:JsonProperty("quarantine")
    @set:JsonProperty("quarantine")
    var quarantine: Boolean? = null
    @JsonProperty("link_flair_text_color")
    @get:JsonProperty("link_flair_text_color")
    @set:JsonProperty("link_flair_text_color")
    var linkFlairTextColor: String? = null
    @JsonProperty("author_flair_background_color")
    @get:JsonProperty("author_flair_background_color")
    @set:JsonProperty("author_flair_background_color")
    var authorFlairBackgroundColor: Any? = null
    @JsonProperty("subreddit_type")
    @get:JsonProperty("subreddit_type")
    @set:JsonProperty("subreddit_type")
    var subredditType: String? = null
    @JsonProperty("ups")
    @get:JsonProperty("ups")
    @set:JsonProperty("ups")
    var ups: Int? = null
    @JsonProperty("total_awards_received")
    @get:JsonProperty("total_awards_received")
    @set:JsonProperty("total_awards_received")
    var totalAwardsReceived: Int? = null
    @JsonProperty("media_embed")
    @get:JsonProperty("media_embed")
    @set:JsonProperty("media_embed")
    var mediaEmbed: MediaEmbed? = null
    @JsonProperty("thumbnail_width")
    @get:JsonProperty("thumbnail_width")
    @set:JsonProperty("thumbnail_width")
    var thumbnailWidth: Int? = null
    @JsonProperty("author_flair_template_id")
    @get:JsonProperty("author_flair_template_id")
    @set:JsonProperty("author_flair_template_id")
    var authorFlairTemplateId: Any? = null
    @JsonProperty("is_original_content")
    @get:JsonProperty("is_original_content")
    @set:JsonProperty("is_original_content")
    var isOriginalContent: Boolean? = null
    @JsonProperty("user_reports")
    @get:JsonProperty("user_reports")
    @set:JsonProperty("user_reports")
    var userReports: List<Any> = ArrayList()
    @JsonProperty("secure_media")
    @get:JsonProperty("secure_media")
    @set:JsonProperty("secure_media")
    var secureMedia: Any? = null
    @JsonProperty("is_reddit_media_domain")
    @get:JsonProperty("is_reddit_media_domain")
    @set:JsonProperty("is_reddit_media_domain")
    var isRedditMediaDomain: Boolean? = null
    @JsonProperty("is_meta")
    @get:JsonProperty("is_meta")
    @set:JsonProperty("is_meta")
    var isMeta: Boolean? = null
    @JsonProperty("category")
    @get:JsonProperty("category")
    @set:JsonProperty("category")
    var category: Any? = null
    @JsonProperty("secure_media_embed")
    @get:JsonProperty("secure_media_embed")
    @set:JsonProperty("secure_media_embed")
    var secureMediaEmbed: SecureMediaEmbed? = null
    @JsonProperty("link_flair_text")
    @get:JsonProperty("link_flair_text")
    @set:JsonProperty("link_flair_text")
    var linkFlairText: Any? = null
    @JsonProperty("can_mod_post")
    @get:JsonProperty("can_mod_post")
    @set:JsonProperty("can_mod_post")
    var canModPost: Boolean? = null
    @JsonProperty("score")
    @get:JsonProperty("score")
    @set:JsonProperty("score")
    var score: Int? = null
    @JsonProperty("approved_by")
    @get:JsonProperty("approved_by")
    @set:JsonProperty("approved_by")
    var approvedBy: Any? = null
    @JsonProperty("thumbnail")
    @get:JsonProperty("thumbnail")
    @set:JsonProperty("thumbnail")
    var thumbnail: String? = null
    @JsonProperty("author_flair_css_class")
    @get:JsonProperty("author_flair_css_class")
    @set:JsonProperty("author_flair_css_class")
    var authorFlairCssClass: Any? = null
    @JsonProperty("author_flair_richtext")
    @get:JsonProperty("author_flair_richtext")
    @set:JsonProperty("author_flair_richtext")
    var authorFlairRichtext: List<Any> = ArrayList()
    @JsonProperty("gildings")
    @get:JsonProperty("gildings")
    @set:JsonProperty("gildings")
    var gildings: Gildings? = null
    @JsonProperty("post_hint")
    @get:JsonProperty("post_hint")
    @set:JsonProperty("post_hint")
    var postHint: String? = null
    @JsonProperty("content_categories")
    @get:JsonProperty("content_categories")
    @set:JsonProperty("content_categories")
    var contentCategories: Any? = null
    @JsonProperty("is_self")
    @get:JsonProperty("is_self")
    @set:JsonProperty("is_self")
    var isSelf: Boolean? = null
    @JsonProperty("mod_note")
    @get:JsonProperty("mod_note")
    @set:JsonProperty("mod_note")
    var modNote: Any? = null
    @JsonProperty("created")
    @get:JsonProperty("created")
    @set:JsonProperty("created")
    var created: Double? = null
    @JsonProperty("link_flair_type")
    @get:JsonProperty("link_flair_type")
    @set:JsonProperty("link_flair_type")
    var linkFlairType: String? = null
    @JsonProperty("wls")
    @get:JsonProperty("wls")
    @set:JsonProperty("wls")
    var wls: Int? = null
    @JsonProperty("banned_by")
    @get:JsonProperty("banned_by")
    @set:JsonProperty("banned_by")
    var bannedBy: Any? = null
    @JsonProperty("author_flair_type")
    @get:JsonProperty("author_flair_type")
    @set:JsonProperty("author_flair_type")
    var authorFlairType: String? = null
    @JsonProperty("domain")
    @get:JsonProperty("domain")
    @set:JsonProperty("domain")
    var domain: String? = null
    @JsonProperty("selftext_html")
    @get:JsonProperty("selftext_html")
    @set:JsonProperty("selftext_html")
    var selftextHtml: Any? = null
    @JsonProperty("likes")
    @get:JsonProperty("likes")
    @set:JsonProperty("likes")
    var likes: Any? = null
    @JsonProperty("suggested_sort")
    @get:JsonProperty("suggested_sort")
    @set:JsonProperty("suggested_sort")
    var suggestedSort: Any? = null
    @JsonProperty("banned_at_utc")
    @get:JsonProperty("banned_at_utc")
    @set:JsonProperty("banned_at_utc")
    var bannedAtUtc: Any? = null
    @JsonProperty("view_count")
    @get:JsonProperty("view_count")
    @set:JsonProperty("view_count")
    var viewCount: Any? = null
    @JsonProperty("archived")
    @get:JsonProperty("archived")
    @set:JsonProperty("archived")
    var archived: Boolean? = null
    @JsonProperty("no_follow")
    @get:JsonProperty("no_follow")
    @set:JsonProperty("no_follow")
    var noFollow: Boolean? = null
    @JsonProperty("is_crosspostable")
    @get:JsonProperty("is_crosspostable")
    @set:JsonProperty("is_crosspostable")
    var isCrosspostable: Boolean? = null
    @JsonProperty("pinned")
    @get:JsonProperty("pinned")
    @set:JsonProperty("pinned")
    var pinned: Boolean? = null
    @JsonProperty("over_18")
    @get:JsonProperty("over_18")
    @set:JsonProperty("over_18")
    var over18: Boolean? = null
    @JsonProperty("preview")
    @get:JsonProperty("preview")
    @set:JsonProperty("preview")
    var preview: Preview? = null
    @JsonProperty("all_awardings")
    @get:JsonProperty("all_awardings")
    @set:JsonProperty("all_awardings")
    var allAwardings: List<AllAwarding> = ArrayList()
    @JsonProperty("media_only")
    @get:JsonProperty("media_only")
    @set:JsonProperty("media_only")
    var mediaOnly: Boolean? = null
    @JsonProperty("can_gild")
    @get:JsonProperty("can_gild")
    @set:JsonProperty("can_gild")
    var canGild: Boolean? = null
    @JsonProperty("spoiler")
    @get:JsonProperty("spoiler")
    @set:JsonProperty("spoiler")
    var spoiler: Boolean? = null
    @JsonProperty("locked")
    @get:JsonProperty("locked")
    @set:JsonProperty("locked")
    var locked: Boolean? = null
    @JsonProperty("author_flair_text")
    @get:JsonProperty("author_flair_text")
    @set:JsonProperty("author_flair_text")
    var authorFlairText: Any? = null
    @JsonProperty("visited")
    @get:JsonProperty("visited")
    @set:JsonProperty("visited")
    var visited: Boolean? = null
    @JsonProperty("num_reports")
    @get:JsonProperty("num_reports")
    @set:JsonProperty("num_reports")
    var numReports: Any? = null
    @JsonProperty("distinguished")
    @get:JsonProperty("distinguished")
    @set:JsonProperty("distinguished")
    var distinguished: Any? = null
    @JsonProperty("subreddit_id")
    @get:JsonProperty("subreddit_id")
    @set:JsonProperty("subreddit_id")
    var subredditId: String? = null
    @JsonProperty("mod_reason_by")
    @get:JsonProperty("mod_reason_by")
    @set:JsonProperty("mod_reason_by")
    var modReasonBy: Any? = null
    @JsonProperty("removal_reason")
    @get:JsonProperty("removal_reason")
    @set:JsonProperty("removal_reason")
    var removalReason: Any? = null
    @JsonProperty("link_flair_background_color")
    @get:JsonProperty("link_flair_background_color")
    @set:JsonProperty("link_flair_background_color")
    var linkFlairBackgroundColor: String? = null
    @JsonProperty("id")
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    var id: String? = null
    @JsonProperty("is_robot_indexable")
    @get:JsonProperty("is_robot_indexable")
    @set:JsonProperty("is_robot_indexable")
    var isRobotIndexable: Boolean? = null
    @JsonProperty("report_reasons")
    @get:JsonProperty("report_reasons")
    @set:JsonProperty("report_reasons")
    var reportReasons: Any? = null
    @JsonProperty("author")
    @get:JsonProperty("author")
    @set:JsonProperty("author")
    var author: String? = null
    @JsonProperty("num_crossposts")
    @get:JsonProperty("num_crossposts")
    @set:JsonProperty("num_crossposts")
    var numCrossposts: Int? = null
    @JsonProperty("num_comments")
    @get:JsonProperty("num_comments")
    @set:JsonProperty("num_comments")
    var numComments: Int? = null
    @JsonProperty("send_replies")
    @get:JsonProperty("send_replies")
    @set:JsonProperty("send_replies")
    var sendReplies: Boolean? = null
    @JsonProperty("whitelist_status")
    @get:JsonProperty("whitelist_status")
    @set:JsonProperty("whitelist_status")
    var whitelistStatus: String? = null
    @JsonProperty("contest_mode")
    @get:JsonProperty("contest_mode")
    @set:JsonProperty("contest_mode")
    var contestMode: Boolean? = null
    @JsonProperty("mod_reports")
    @get:JsonProperty("mod_reports")
    @set:JsonProperty("mod_reports")
    var modReports: List<Any> = ArrayList()
    @JsonProperty("author_patreon_flair")
    @get:JsonProperty("author_patreon_flair")
    @set:JsonProperty("author_patreon_flair")
    var authorPatreonFlair: Boolean? = null
    @JsonProperty("author_flair_text_color")
    @get:JsonProperty("author_flair_text_color")
    @set:JsonProperty("author_flair_text_color")
    var authorFlairTextColor: Any? = null
    @JsonProperty("permalink")
    @get:JsonProperty("permalink")
    @set:JsonProperty("permalink")
    var permalink: String? = null
    @JsonProperty("parent_whitelist_status")
    @get:JsonProperty("parent_whitelist_status")
    @set:JsonProperty("parent_whitelist_status")
    var parentWhitelistStatus: String? = null
    @JsonProperty("stickied")
    @get:JsonProperty("stickied")
    @set:JsonProperty("stickied")
    var stickied: Boolean? = null
    @JsonProperty("url")
    @get:JsonProperty("url")
    @set:JsonProperty("url")
    var url: String? = null
    @JsonProperty("subreddit_subscribers")
    @get:JsonProperty("subreddit_subscribers")
    @set:JsonProperty("subreddit_subscribers")
    var subredditSubscribers: Int? = null
    @JsonProperty("created_utc")
    @get:JsonProperty("created_utc")
    @set:JsonProperty("created_utc")
    var createdUtc: Double? = null
    @JsonProperty("media")
    @get:JsonProperty("media")
    @set:JsonProperty("media")
    var media: Any? = null
    @JsonProperty("is_video")
    @get:JsonProperty("is_video")
    @set:JsonProperty("is_video")
    var isVideo: Boolean? = null
    @JsonProperty("author_cakeday")
    @get:JsonProperty("author_cakeday")
    @set:JsonProperty("author_cakeday")
    var authorCakeday: Boolean? = null

}
