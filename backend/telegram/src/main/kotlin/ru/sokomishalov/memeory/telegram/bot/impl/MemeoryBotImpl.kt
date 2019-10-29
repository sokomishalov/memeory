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
import ru.sokomishalov.commons.core.common.unit
import ru.sokomishalov.commons.core.log.Loggable
import ru.sokomishalov.memeory.core.dto.MemeDTO
import ru.sokomishalov.memeory.core.enums.AttachmentType.*
import ru.sokomishalov.memeory.db.BotUserService
import ru.sokomishalov.memeory.telegram.autoconfigure.TelegramBotProperties
import ru.sokomishalov.memeory.telegram.bot.MemeoryBot
import ru.sokomishalov.memeory.telegram.enum.Commands
import ru.sokomishalov.memeory.telegram.util.api.extractUserInfo
import ru.sokomishalov.memeory.telegram.util.api.sendMediaGroup
import ru.sokomishalov.memeory.telegram.util.api.sendMessage
import ru.sokomishalov.memeory.telegram.util.api.sendPhoto

class MemeoryBotImpl(
        private val props: TelegramBotProperties,
        private val botUserService: BotUserService
) : TelegramLongPollingBot(), MemeoryBot, Loggable {

    override fun getBotUsername(): String = props.username ?: throw IllegalArgumentException()
    override fun getBotToken(): String = props.token ?: throw IllegalArgumentException()
    override fun onUpdateReceived(update: Update) = GlobalScope.launch { receiveMessage(update.message) }.unit()

    override suspend fun receiveMessage(message: Message) {
        when {
            Commands.START.cmd in message.text.orEmpty() -> {
                botUserService.save(message.extractUserInfo())
                sendMessage(SendMessage(message.chatId, "Hello"))
            }
            else -> logInfo("Unsupported action ${message.text}")
        }
        unit()
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
                            NONE -> unit()
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