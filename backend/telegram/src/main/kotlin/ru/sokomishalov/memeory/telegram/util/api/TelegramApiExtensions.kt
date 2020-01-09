package ru.sokomishalov.memeory.telegram.util.api

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.bots.AbsSender
import ru.sokomishalov.commons.core.log.loggerFor
import ru.sokomishalov.memeory.core.dto.BotUserDTO

/**
 * @author sokomishalov
 */
private val log = loggerFor("TelegramApiUtils")

fun initTelegramApi() = ApiContextInitializer.init()

@PublishedApi
internal fun Message.extractUserInfo(): BotUserDTO {
    return BotUserDTO(
            username = from.userName,
            fullName = "${from.lastName} ${from.firstName}",
            languageCode = from.languageCode,
            chatId = chatId
    )
}

@PublishedApi
internal suspend fun AbsSender.sendMessage(message: SendMessage): Message? = withContext(IO) {
    runCatching {
        execute(message)
    }.onFailure {
        log.warn(it.message, it)
    }.getOrNull()
}

@PublishedApi
internal suspend fun AbsSender.sendEditMessageText(message: EditMessageText) = withContext(IO) {
    runCatching {
        execute(message)
    }.onFailure {
        log.warn(it.message, it)
    }.getOrNull()
}

@PublishedApi
internal suspend fun AbsSender.sendPhoto(photo: SendPhoto): Message? = withContext(IO) {
    runCatching {
        execute(photo)
    }.onFailure {
        log.warn(it.message, it)
    }.getOrNull()
}

@PublishedApi
internal suspend fun AbsSender.sendMediaGroup(mediaGroup: SendMediaGroup): List<Message> = withContext(IO) {
    runCatching {
        execute(mediaGroup)
    }.onFailure {
        log.warn(it.message, it)
    }.getOrElse {
        emptyList()
    }
}

@PublishedApi
internal suspend fun AbsSender.sendEditMessageReplyMarkup(replyMarkup: EditMessageReplyMarkup) = withContext(IO) {
    runCatching {
        execute(replyMarkup)
    }.onFailure {
        log.warn(it.message, it)
    }.getOrNull()
}
