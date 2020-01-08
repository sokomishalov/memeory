@file:Suppress("RemoveRedundantQualifierName")

package ru.sokomishalov.memeory.telegram.bot.impl

import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.meta.ApiContext
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.sokomishalov.commons.core.log.Loggable
import ru.sokomishalov.memeory.core.dto.MemeDTO
import ru.sokomishalov.memeory.core.enums.AttachmentType.*
import ru.sokomishalov.memeory.db.BotUserService
import ru.sokomishalov.memeory.db.TopicService
import ru.sokomishalov.memeory.telegram.autoconfigure.TelegramBotProperties
import ru.sokomishalov.memeory.telegram.bot.MemeoryBot
import ru.sokomishalov.memeory.telegram.enum.Commands.START
import ru.sokomishalov.memeory.telegram.enum.FilterType
import ru.sokomishalov.memeory.telegram.enum.FilterType.TOPICS
import ru.sokomishalov.memeory.telegram.util.api.extractUserInfo
import ru.sokomishalov.memeory.telegram.util.api.sendMediaGroup
import ru.sokomishalov.memeory.telegram.util.api.sendMessage
import ru.sokomishalov.memeory.telegram.util.api.sendPhoto

@Suppress("unused")
class MemeoryBotImpl(
        private val props: TelegramBotProperties,
        private val botUserService: BotUserService,
        private val topicService: TopicService
) : DefaultAbsSender(ApiContext.getInstance(DefaultBotOptions::class.java)), MemeoryBot {

    companion object : Loggable

    override fun getBotToken(): String = requireNotNull(props.token)

    override suspend fun receiveUpdate(update: Update) {
        if (update.message == null) {
            val query = update.callbackQuery.data
            when {
                query == TOPICS.type -> drawTopics(update)
            }
        } else {
            val message = update.message
            when (message.text.orEmpty()) {
                START.cmd -> startCommand(message)
                else -> unknownCommand(message)
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
                            IMAGE -> sendPhoto(SendPhoto().apply {
                                chatId = c
                                caption = m.caption
                                photo = InputFile(a.url)
                            })
                            VIDEO -> sendMessage(SendMessage().apply {
                                chatId = c
                                text = "${m.caption} \n\n ${a.url}"
                            })
                            NONE -> Unit
                        }
                    }
                    else -> sendMediaGroup(SendMediaGroup().apply {
                        chatId = c
                        media = m.attachments.mapNotNull { a ->
                            when (a.type) {
                                IMAGE -> InputMediaPhoto().apply {
                                    media = a.url
                                    caption = m.caption
                                }
                                VIDEO,
                                NONE -> null
                            }
                        }
                    })
                }
            }
        }
    }

    private suspend fun drawTopics(update: Update) {
        sendMessage(SendMessage().apply {
            chatId = update.message.chatId.toString()
            text = "Choose your relevant topics:"
        })
    }

    private suspend fun unknownCommand(message: Message) {
        sendMessage(SendMessage().apply {
            chatId = message.chatId.toString()
            text = "Unsupported type of message '${message.text}' :("
            replyToMessageId = message.messageId
        })
    }

    private suspend fun startCommand(message: Message) {
        val botUser = message.extractUserInfo()
        botUserService.save(botUser)
        logInfo("Registered user ${botUser.fullName}")
        sendMessage(SendMessage().apply {
            chatId = message.chatId.toString()
            text = "Choose customization types:"
            replyToMessageId = message.messageId
            replyMarkup = InlineKeyboardMarkup().apply {
                keyboard = listOf(FilterType.values().map {
                    InlineKeyboardButton().apply {
                        text = it.type.capitalize()
                        callbackData = it.type
                    }
                })
            }
        })
    }
}