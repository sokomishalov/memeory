package ru.sokomishalov.memeory.telegram.util.api

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.Message
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

internal suspend fun DefaultAbsSender.sendMessage(message: SendMessage): Message? = withContext(IO) {
    runCatching {
        execute(message)
    }.onFailure {
        log.warn(it.message, it)
    }.getOrNull()
}

internal suspend fun DefaultAbsSender.sendPhoto(photo: SendPhoto): Message? = withContext(IO) {
    runCatching {
        execute(photo)
    }.onFailure {
        log.warn(it.message, it)
    }.getOrNull()
}

internal suspend fun DefaultAbsSender.sendMediaGroup(mediaGroup: SendMediaGroup): List<Message> = withContext(IO) {
    runCatching {
        execute(mediaGroup)
    }.onFailure {
        log.warn(it.message, it)
    }.getOrElse {
        emptyList()
    }
}