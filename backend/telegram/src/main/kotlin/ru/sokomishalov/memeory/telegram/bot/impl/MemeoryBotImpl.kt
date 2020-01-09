@file:Suppress("RemoveRedundantQualifierName")

package ru.sokomishalov.memeory.telegram.bot.impl

import com.fasterxml.jackson.module.kotlin.readValue
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.meta.ApiContext
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.sokomishalov.commons.core.log.Loggable
import ru.sokomishalov.commons.core.serialization.OBJECT_MAPPER
import ru.sokomishalov.memeory.core.dto.BotUserDTO
import ru.sokomishalov.memeory.core.dto.MemeDTO
import ru.sokomishalov.memeory.core.enums.AttachmentType.*
import ru.sokomishalov.memeory.db.BotUserService
import ru.sokomishalov.memeory.db.ChannelService
import ru.sokomishalov.memeory.db.TopicService
import ru.sokomishalov.memeory.telegram.autoconfigure.TelegramBotProperties
import ru.sokomishalov.memeory.telegram.bot.MemeoryBot
import ru.sokomishalov.memeory.telegram.dto.BotCallbackQueryDTO
import ru.sokomishalov.memeory.telegram.enum.Commands.CUSTOMIZE
import ru.sokomishalov.memeory.telegram.enum.Commands.START
import ru.sokomishalov.memeory.telegram.enum.FilterType
import ru.sokomishalov.memeory.telegram.enum.FilterType.*
import ru.sokomishalov.memeory.telegram.util.api.*

/**
 * @author sokomishalov
 *
 * todo refactor this
 */
class MemeoryBotImpl(
        private val props: TelegramBotProperties,
        private val botUserService: BotUserService,
        private val topicService: TopicService,
        private val channelService: ChannelService,
        botOptions: DefaultBotOptions = ApiContext.getInstance(DefaultBotOptions::class.java)
) : DefaultAbsSender(botOptions), MemeoryBot {

    companion object : Loggable {
        const val ACTIVE = "✅"
        const val INACTIVE = "❌"
    }

    override fun getBotToken(): String = requireNotNull(props.token)

    override suspend fun receiveUpdate(update: Update) {
        if (update.message == null) {
            val query = update.callbackQuery.data.deserializeCallbackQuery()

            when (query.filterType) {
                TOPICS -> {
                    when {
                        query.id.isNullOrEmpty() -> drawTopics(update)
                        else -> updateDrawnTopics(update, query)
                    }
                }
                PROVIDERS -> Unit
                CHANNELS -> Unit
                null -> customizeCmd(update.callbackQuery.message, query)
            }
        } else {
            val message = update.message
            when (message.text.orEmpty()) {
                START.cmd -> startCmd(message)
                CUSTOMIZE.cmd -> customizeCmd(message)
                else -> unknownCmd(message)
            }
        }
    }

    override suspend fun broadcastMemes(memes: List<MemeDTO>) {
        val users = botUserService.findAll()
        val channels = channelService.findAll()

        users.forEach { u ->
            val userRelevantChannels = channels
                    .filter { (u.topics intersect it.topics).isNotEmpty() }
                    .map { it.id }

            memes
                    .filter { it.channelId in userRelevantChannels }
                    .forEach { m ->
                        when {
                            m.attachments.size <= 1 -> sendSingleAttachment(m, u)
                            else -> sendMultipleAttachments(m, u)
                        }
                    }
        }
    }

    private suspend fun startCmd(message: Message) {
        val botUser = message.extractUserInfo()
        botUserService.save(botUser)
        logInfo("Registered user ${botUser.fullName}")
        customizeCmd(message)
    }

    private suspend fun customizeCmd(message: Message, query: BotCallbackQueryDTO? = null) {
        val chat = message.chatId.toString()
        val caption = "Choose customization types:"
        val keyboardMarkup = InlineKeyboardMarkup().apply {
            keyboard = listOf(FilterType.values().map {
                val itemQuery = BotCallbackQueryDTO(filterType = it)
                InlineKeyboardButton().apply {
                    text = it.type.capitalize()
                    callbackData = itemQuery.serialize()
                }
            })
        }

        if (query == null) {
            sendMessage(SendMessage().apply {
                chatId = chat
                text = caption
                replyToMessageId = message.messageId
                replyMarkup = keyboardMarkup
            })
        } else {
            sendEditMessageText(EditMessageText().apply {
                chatId = chat
                messageId = message.messageId
                text = caption
                replyMarkup = keyboardMarkup
            })
        }
    }

    private suspend fun unknownCmd(message: Message) {
        sendMessage(SendMessage().apply {
            chatId = message.chatId.toString()
            text = "Unsupported type of message '${message.text}' :("
            replyToMessageId = message.messageId
        })
    }

    private suspend fun sendMultipleAttachments(meme: MemeDTO, botUser: BotUserDTO) {
        sendMediaGroup(SendMediaGroup().apply {
            chatId = botUser.chatId.toString()
            media = meme.attachments.mapNotNull { a ->
                when (a.type) {
                    IMAGE -> InputMediaPhoto().apply {
                        media = a.url
                        caption = meme.caption
                    }
                    VIDEO,
                    NONE -> null
                }
            }
        })
    }

    private suspend fun sendSingleAttachment(meme: MemeDTO, botUser: BotUserDTO) {
        val a = meme.attachments.firstOrNull()
        when (a?.type) {
            IMAGE -> sendPhoto(SendPhoto().apply {
                chatId = botUser.chatId.toString()
                caption = meme.caption
                photo = InputFile(a.url)
            })
            VIDEO -> sendMessage(SendMessage().apply {
                chatId = botUser.chatId.toString()
                text = "${meme.caption} \n\n ${a.url}"
            })
            NONE -> Unit
        }
    }

    private suspend fun drawTopics(update: Update) {
        val callbackQuery = update.callbackQuery

        val topics = topicService.findAll()
        val botUser = botUserService.findByUsername(callbackQuery.message.chat.userName)
        val activeTopics = botUser?.topics.orEmpty()

        sendEditMessageText(EditMessageText().apply {
            chatId = callbackQuery.message.chatId.toString()
            messageId = callbackQuery.message.messageId
            text = "Choose your relevant topics:"
            replyMarkup = InlineKeyboardMarkup().apply {
                val buttons: MutableList<List<InlineKeyboardButton>> = topics
                        .map {
                            val query = BotCallbackQueryDTO(filterType = TOPICS, id = it.id)
                            val active = when (query.id) {
                                in activeTopics -> ACTIVE
                                else -> INACTIVE
                            }

                            InlineKeyboardButton().apply {
                                text = "$active ${it.caption}"
                                callbackData = query.serialize()
                            }
                        }
                        .chunked(2)
                        .toMutableList()

                buttons += listOf(InlineKeyboardButton().apply {
                    text = "Back"
                    callbackData = BotCallbackQueryDTO().serialize()
                })

                keyboard = buttons
            }
        })
    }

    private suspend fun updateDrawnTopics(update: Update, query: BotCallbackQueryDTO) {
        val callbackQuery = update.callbackQuery

        val botUser = botUserService.toggleTopic(callbackQuery.message.chat.userName, query.id.orEmpty())
        val activeTopics = botUser?.topics.orEmpty()

        sendEditMessageReplyMarkup(EditMessageReplyMarkup().apply {
            chatId = callbackQuery.message.chatId.toString()
            messageId = callbackQuery.message.messageId
            replyMarkup = callbackQuery.message.replyMarkup.apply {
                keyboard.forEach { rows ->
                    rows.forEach { item ->
                        val itemQuery = item.callbackData.deserializeCallbackQuery()
                        item.text = when (itemQuery.id) {
                            in activeTopics -> item.text.replace(INACTIVE, ACTIVE)
                            else -> item.text.replace(ACTIVE, INACTIVE)
                        }
                    }
                }
            }
        })
    }

    private fun String.deserializeCallbackQuery(): BotCallbackQueryDTO = OBJECT_MAPPER.readValue(this)
    private fun BotCallbackQueryDTO.serialize(): String = OBJECT_MAPPER.writeValueAsString(this)
}