@file:Suppress("unused")

package ru.sokomishalov.memeory.util

import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ofPattern

// Strings
const val EMPTY: String = ""
const val ID_DELIMITER: String = ":"
const val MONGO_ID_FIELD: String = "_id"
const val COROUTINE_PATH_PREFIX = "/experimental"
const val MEMEORY_TOKEN_HEADER = "MEMEORY_TOKEN"

const val FACEBOOK_BASE_URl: String = "https://www.facebook.com"
const val FACEBOOK_GRAPH_BASE_URl: String = "http://graph.facebook.com"
const val REDDIT_BASE_URl: String = "https://www.reddit.com"
const val TWITTER_URL: String = "https://twitter.com"

const val HORIZONTAL_ORIENTATION = "HORIZONTAL"
const val VERTICAL_ORIENTATION = "VERTICAL"

// Keys
const val CHANNEL_LOGO_CACHE_KEY: String = "CHANNEL_LOGO"

// Date formatters
val DATE_FORMATTER: DateTimeFormatter = ofPattern("yyyy-MM-dd HH:mm:ss")

