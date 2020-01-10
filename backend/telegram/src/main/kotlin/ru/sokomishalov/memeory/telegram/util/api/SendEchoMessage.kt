package ru.sokomishalov.memeory.telegram.util.api

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

/**
 * @author sokomishalov
 */
internal class SendEchoMessage(update: Update) : SendMessage(update.message.chatId, update.message.text)