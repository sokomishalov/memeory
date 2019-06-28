package ru.sokomishalov.memeory.util

import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ofPattern

// Strings
const val EMPTY: String = ""
const val ID_DELIMITER: String = ":"

const val FACEBOOK_BASE_URl: String = "https://www.facebook.com"
const val FACEBOOK_GRAPH_BASE_URl: String = "http://graph.facebook.com"
const val REDDIT_BASE_URl: String = "https://www.reddit.com"
const val TWITTER_URL = "https://twitter.com"

// Keys
const val CHANNEL_LOGO_CACHE_KEY: String = "CHANNEL_LOGO"

// Date formatters
val DATE_FORMATTER: DateTimeFormatter = ofPattern("yyyy-MM-dd HH:mm:ss")

