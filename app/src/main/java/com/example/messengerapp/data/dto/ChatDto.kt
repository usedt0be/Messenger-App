package com.example.messengerapp.data.dto


data class ChatDto(
    val chatId: String,
    val userIds: List<String>,
    val messages: List<MessageDto>
)
