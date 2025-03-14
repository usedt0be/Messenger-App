package com.example.messengerapp.data.mappers

import com.example.messengerapp.data.dto.ChatDto
import com.example.messengerapp.data.dto.MessageDto
import com.example.messengerapp.domain.models.Chat
import com.example.messengerapp.domain.models.Message


fun ChatDto.toChat(): Chat =
    Chat(
        chatId = chatId,
        userIds = userIds,
        messages = messages?.map { it.toMessage() }
    )


fun MessageDto.toMessage(): Message =
    Message(
        messageId = messageId,
        senderId = senderId,
        text = text,
        time = timestamp
    )