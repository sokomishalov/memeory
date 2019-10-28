package ru.sokomishalov.memeory.telegram.bot.impl

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.methods.send.SendVideo
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto
import org.telegram.telegrambots.meta.api.objects.media.InputMediaVideo
import ru.sokomishalov.commons.core.common.unit
import ru.sokomishalov.commons.core.log.Loggable
import ru.sokomishalov.memeory.core.dto.BotUserDTO
import ru.sokomishalov.memeory.core.dto.MemeDTO
import ru.sokomishalov.memeory.core.enums.AttachmentType.*
import ru.sokomishalov.memeory.db.BotUserService
import ru.sokomishalov.memeory.telegram.autoconfigure.TelegramBotProperties
import ru.sokomishalov.memeory.telegram.bot.MemeoryBot
import ru.sokomishalov.memeory.telegram.enum.Commands

class MemeoryBotImpl(
        private val props: TelegramBotProperties,
        private val botUserService: BotUserService
) : TelegramLongPollingBot(), Loggable, MemeoryBot {

    override fun getBotUsername(): String = props.username ?: throw IllegalArgumentException()
    override fun getBotToken(): String = props.token ?: throw IllegalArgumentException()

    override fun onUpdateReceived(update: Update) {
        GlobalScope.launch {
            when {
                Commands.START.cmd in update.message.text -> {
                    botUserService.save(update.extractUserInfo())
                    execute(SendMessage(update.message.chatId, "Hello"))
                }
                else -> logInfo("Unsupported action ${update.message.text}")
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
                            IMAGE -> execute(SendPhoto().apply { chatId = c; caption = m.caption; setPhoto(a.url) })
                            VIDEO -> execute(SendVideo().apply { chatId = c; caption = m.caption; setVideo(a.url) })
                            NONE -> unit()
                        }
                    }
                    else -> execute(SendMediaGroup().apply {
                        chatId = c
                        media = m.attachments.mapNotNull { a ->
                            when (a.type) {
                                IMAGE -> InputMediaPhoto().apply { media = a.url; caption = m.caption; }
                                VIDEO -> InputMediaVideo().apply { media = a.url; caption = m.caption; }
                                NONE -> null
                            }
                        }
                    })
                }
            }
        }
    }

    private fun Update.extractUserInfo(): BotUserDTO {
        val from = message.from
        return BotUserDTO(
                id = from.id.toLong(),
                username = from.userName,
                fullName = "${from.lastName} ${from.firstName}",
                languageCode = from.languageCode,
                chatId = message.chatId
        )
    }
}