package ru.sokomishalov.memeory.telegram.util.api

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.*
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.bots.AbsSender
import ru.sokomishalov.commons.core.log.loggerFor
import ru.sokomishalov.memeory.core.dto.BotUserDTO
import java.io.Serializable

/**
 * @author sokomishalov
 */
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
internal suspend inline fun <T : Serializable> AbsSender.send(method: PartialBotApiMethod<T>) = withContext(IO) {
    runCatching {
        when (method) {
            is SendMediaGroup -> execute(method)
            is SendPhoto -> execute(method)
            is SendVideo -> execute(method)
            is SendAnimation -> execute(method)
            is SendSticker -> execute(method)
            //...
            is BotApiMethod<T> -> execute(method)
            else -> log.warn("Unknown method")
        }
    }.onFailure {
        log.warn(it.message, it)
    }.getOrElse {
        Unit
    }
}

@PublishedApi
internal val log = loggerFor("TelegramApiExtensions")