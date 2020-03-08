@file:Suppress("RemoveRedundantQualifierName")

package ru.sokomishalov.memeory.telegram.bot.impl

import com.fasterxml.jackson.module.kotlin.readValue
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.meta.ApiContext
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
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
import ru.sokomishalov.memeory.core.dto.ChannelDTO
import ru.sokomishalov.memeory.core.dto.MemeDTO
import ru.sokomishalov.memeory.core.dto.MemesPageRequestDTO
import ru.sokomishalov.memeory.core.enums.AttachmentType.*
import ru.sokomishalov.memeory.db.BotUserService
import ru.sokomishalov.memeory.db.ChannelService
import ru.sokomishalov.memeory.db.MemeService
import ru.sokomishalov.memeory.db.TopicService
import ru.sokomishalov.memeory.telegram.autoconfigure.TelegramBotProperties
import ru.sokomishalov.memeory.telegram.bot.MemeoryBot
import ru.sokomishalov.memeory.telegram.dto.BotCallbackQueryDTO
import ru.sokomishalov.memeory.telegram.enum.BotScreen.*
import ru.sokomishalov.memeory.telegram.enum.Commands.*
import ru.sokomishalov.memeory.telegram.util.api.extractUserInfo
import ru.sokomishalov.memeory.telegram.util.api.send

/**
 * @author sokomishalov
 *
 * todo refactor this
 */
class MemeoryBotImpl(
        private val props: TelegramBotProperties,
        private val memeService: MemeService,
        private val botUserService: BotUserService,
        private val topicService: TopicService,
        private val channelService: ChannelService,
        botOptions: DefaultBotOptions = ApiContext.getInstance(DefaultBotOptions::class.java)
) : DefaultAbsSender(botOptions), MemeoryBot {

    companion object : Loggable {
        const val ACTIVE = "✅"
        const val INACTIVE = "❌"
        const val BACK = "⬅️"
    }

    override fun getBotToken(): String = requireNotNull(props.token)

    override suspend fun receiveUpdate(update: Update): BotApiMethod<*>? {
        return if (update.message == null) {
            val query = update.callbackQuery.data.deserializeCallbackQuery()

            when (query.screen) {
                TOPICS -> {
                    when {
                        query.id.isNullOrEmpty() -> drawTopics(update)
                        else -> updateDrawnTopics(update, query)
                    }
                }
                PROVIDERS -> unknownCmd(update.callbackQuery.message)
                CHANNELS -> unknownCmd(update.callbackQuery.message)
                null -> customizeCmd(update.callbackQuery.message, query)
            }
        } else {
            val message = update.message
            val text = message.text.orEmpty()

            when {
                text == START.cmd -> startCmd(message)
                text == CUSTOMIZE.cmd -> customizeCmd(message)
                text.startsWith(RANDOM.cmd) -> randomCmd(message)
                else -> unknownCmd(message)
            }
        }
    }

    override suspend fun broadcastMemes(memes: List<MemeDTO>) {
        val users = botUserService.findAll()
        val channels = channelService.findAll()

        users.forEach { user ->
            val relevantChannels = channels
                    .filter { (user.topics intersect it.topics).isNotEmpty() }
                    .map { it.id }

            memes
                    .filter { it.channelId in relevantChannels }
                    .forEach {
                        val memeChannel = channels.find { c -> c.id == it.channelId }
                        it.sendTo(user, memeChannel)
                    }
        }
    }

    private suspend fun startCmd(message: Message): BotApiMethod<*> {
        val botUser = message.extractUserInfo()
        botUserService.save(botUser)
        logInfo("Registered user ${botUser.fullName}")
        return customizeCmd(message)
    }

    private fun customizeCmd(message: Message, query: BotCallbackQueryDTO? = null): BotApiMethod<*> {
        val chat = message.chatId.toString()
        val caption = "Choose customization types:"
        val keyboardMarkup = InlineKeyboardMarkup().apply {
            keyboard = listOf(listOf(TOPICS).map {
                val itemQuery = BotCallbackQueryDTO(screen = it)
                InlineKeyboardButton().apply {
                    text = it.type
                    callbackData = itemQuery.serialize()
                }
            })
        }

        return when (query) {
            null -> SendMessage().apply {
                chatId = chat
                text = caption
                replyToMessageId = message.messageId
                replyMarkup = keyboardMarkup
            }
            else -> EditMessageText().apply {
                chatId = chat
                messageId = message.messageId
                text = caption
                replyMarkup = keyboardMarkup
            }
        }
    }

    private suspend fun randomCmd(message: Message?): BotApiMethod<*>? {
        val username = message?.extractUserInfo()?.username.orEmpty()
        val amount = message?.text.orEmpty().removePrefix(RANDOM.cmd).toIntOrNull() ?: 1
        val botUser = botUserService.findByUsername(username)

        return when {
            botUser == null || botUser.topics.isEmpty() -> SendMessage().apply {
                chatId = message?.chatId.toString()
                text = "Select relevant channels first. Use ${START.cmd} or ${CUSTOMIZE.cmd} command"
            }
            else -> {
                val pageRequest = MemesPageRequestDTO(topicId = botUser.topics.random(), pageNumber = 0, pageSize = amount)
                val memes = memeService.getPage(pageRequest)
                val channels = channelService.findAll()
                memes.forEach {
                    val memeChannel = channels.find { c -> c.id == it.channelId }
                    it.sendTo(botUser, memeChannel)
                }
                null
            }
        }
    }

    private fun unknownCmd(message: Message): BotApiMethod<*> {
        return SendMessage().apply {
            chatId = message.chatId.toString()
            text = "Unsupported action so far :("
            replyToMessageId = message.messageId
        }
    }

    private suspend fun drawTopics(update: Update): BotApiMethod<*> {
        val callbackQuery = update.callbackQuery

        val topics = topicService.findAll()
        val botUser = botUserService.findByUsername(callbackQuery.message.chat.userName)
        val activeTopics = botUser?.topics.orEmpty()

        return EditMessageText().apply {
            chatId = callbackQuery.message.chatId.toString()
            messageId = callbackQuery.message.messageId
            text = "Choose your relevant topics:"
            replyMarkup = InlineKeyboardMarkup().apply {
                val buttons: MutableList<List<InlineKeyboardButton>> = topics
                        .map {
                            val query = BotCallbackQueryDTO(screen = TOPICS, id = it.id)
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
                    text = "$BACK Back"
                    callbackData = BotCallbackQueryDTO().serialize()
                })

                keyboard = buttons
            }
        }
    }

    private suspend fun updateDrawnTopics(update: Update, query: BotCallbackQueryDTO): BotApiMethod<*> {
        val callbackQuery = update.callbackQuery

        val botUser = botUserService.toggleTopic(callbackQuery.message.chat.userName, query.id.orEmpty())
        val activeTopics = botUser?.topics.orEmpty()

        return EditMessageReplyMarkup().apply {
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
        }
    }

    private suspend fun MemeDTO.sendTo(botUser: BotUserDTO, memeChannel: ChannelDTO?) {
        val meme = this
        when {
            meme.attachments.size <= 1 -> {
                val a = meme.attachments.firstOrNull()
                when (a?.type) {
                    IMAGE -> send(SendPhoto().apply {
                        chatId = botUser.chatId.toString()
                        caption = meme.caption.addCaptionSuffix(meme, memeChannel)
                        photo = InputFile(a.url)
                        parseMode = "markdown"
                    })
                    VIDEO -> send(SendMessage().apply {
                        chatId = botUser.chatId.toString()
                        text = "${meme.caption} \n\n ${a.url}".addCaptionSuffix(meme, memeChannel)
                        setParseMode("markdown")
                    })
                    NONE -> Unit
                }
            }
            else -> {
                send(SendMediaGroup().apply {
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
        }

    }

    private fun String?.addCaptionSuffix(meme: MemeDTO, memeChannel: ChannelDTO?): String {
        val channelHashTag = meme.channelId.cleanUpForHashTag()
        val topicNames = memeChannel?.topics?.firstOrNull().cleanUpForHashTag()
        val provider = memeChannel?.provider?.name.cleanUpForHashTag()

        return """
                  |${this.orEmpty()}
                  |
                  |*Channel:* $channelHashTag 
                  |*Topic:* $topicNames
                  |*Provider:* $provider
                  |
                  |""".trimMargin()
    }

    private fun String?.cleanUpForHashTag(): String = this.orEmpty().filter { it.isLetterOrDigit() }.toLowerCase().let { "#${it}" }
    private fun String.deserializeCallbackQuery(): BotCallbackQueryDTO = OBJECT_MAPPER.readValue(this)
    private fun BotCallbackQueryDTO.serialize(): String = OBJECT_MAPPER.writeValueAsString(this)
}