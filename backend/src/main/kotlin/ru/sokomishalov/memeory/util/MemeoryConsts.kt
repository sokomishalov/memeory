package ru.sokomishalov.memeory.util

import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ofPattern

const val EMPTY: String = ""
const val ID_DELIMITER: String = ":"

val DATE_FORMATTER: DateTimeFormatter = ofPattern("yyyy-MM-dd HH:mm:ss")
