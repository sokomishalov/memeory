package ru.sokomishalov.memeory.telegram.api

import org.telegram.telegrambots.ApiContextInitializer

/**
 * @author sokomishalov
 */
fun initTelegramApi() = ApiContextInitializer.init()