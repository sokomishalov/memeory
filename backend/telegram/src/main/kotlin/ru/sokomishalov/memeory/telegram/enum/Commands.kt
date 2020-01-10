package ru.sokomishalov.memeory.telegram.enum

enum class Commands(val cmd: String) {
    START("/start"),
    CUSTOMIZE("/customize"),
    RANDOM("/random")
}