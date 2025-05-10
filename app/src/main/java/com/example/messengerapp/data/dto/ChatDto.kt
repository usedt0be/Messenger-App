package com.example.messengerapp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ChatDto(
    val chatId: String,
    val participantsIds: List<String>,
    val messages: List<MessageDto>? = emptyList()
)
