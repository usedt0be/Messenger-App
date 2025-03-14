package com.example.messengerapp.domain.models

data class Chat(
    val chatId: String,
    val userIds: List<String>,
    val messages: List<Message>?
)
