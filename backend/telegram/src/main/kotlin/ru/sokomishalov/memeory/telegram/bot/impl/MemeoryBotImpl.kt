package ru.sokomishalov.memeory.telegram.bot.impl

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import ru.sokomishalov.commons.core.common.unit
import ru.sokomishalov.commons.core.log.Loggable
import ru.sokomishalov.memeory.core.dto.MemeDTO
import ru.sokomishalov.memeory.core.enums.AttachmentType.*
import ru.sokomishalov.memeory.db.BotUserService
import ru.sokomishalov.memeory.telegram.autoconfigure.TelegramBotProperties
import ru.sokomishalov.memeory.telegram.bot.MemeoryBot
import ru.sokomishalov.memeory.telegram.enum.Commands.START
import ru.sokomishalov.memeory.telegram.util.api.extractUserInfo
import ru.sokomishalov.memeory.telegram.util.api.sendMediaGroup
import ru.sokomishalov.memeory.telegram.util.api.sendMessage
import ru.sokomishalov.memeory.telegram.util.api.sendPhoto

class MemeoryBotImpl(
        private val props: TelegramBotProperties,
        private val botUserService: BotUserService
) : TelegramLongPollingBot(), MemeoryBot {

    companion object : Loggable

    override fun getBotUsername(): String = requireNotNull(props.username)
    override fun getBotToken(): String = requireNotNull(props.token)
    override fun onUpdateReceived(update: Update) = GlobalScope.launch { receiveMessage(update.message) }.unit()

    override suspend fun receiveMessage(message: Message) {
        when (message.text.orEmpty()) {
            START.cmd -> {
                val botUser = message.extractUserInfo()
                botUserService.save(botUser)
                logInfo("Registered user ${botUser.fullName}")
                sendMessage(SendMessage().apply {
                    chatId = message.chatId.toString()
                    text = "Hello"
                    replyToMessageId = message.messageId
                    replyMarkup = InlineKeyboardMarkup().apply {
                        // todo
                        keyboard = emptyList()
                    }
                })
            }
            else -> {
                sendMessage(SendMessage().apply {
                    chatId = message.chatId.toString()
                    text = "Unsupported action ${message.text} :("
                    replyToMessageId = message.messageId
                    replyMarkup = InlineKeyboardMarkup().apply {
                        keyboard = emptyList()
                    }
                })
            }
        }
    }

    override suspend fun broadcastMemes(memes: List<MemeDTO>) {
        val users = botUserService.findAll()
        val chats = users.map { it.chatId.toString() }

        chats.forEach { c ->
            memes.forEach { m ->
                when {
                    m.attachments.size <= 1 -> {
                        val a = m.attachments.firstOrNull()
                        when (a?.type) {
                            IMAGE -> sendPhoto(SendPhoto().apply { chatId = c; caption = m.caption; setPhoto(a.url) })
                            VIDEO -> sendMessage(SendMessage(c, "${m.caption} \n\n ${a.url}"))
                            NONE -> Unit
                        }
                    }
                    else -> sendMediaGroup(SendMediaGroup().apply {
                        chatId = c
                        media = m.attachments.mapNotNull { a ->
                            when (a.type) {
                                IMAGE -> InputMediaPhoto().apply { media = a.url; caption = m.caption; }
                                VIDEO,
                                NONE -> null
                            }
                        }
                    })
                }
            }
        }
    }
}