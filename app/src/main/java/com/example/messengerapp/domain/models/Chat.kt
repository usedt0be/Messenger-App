package com.example.messengerapp.domain.models

import androidx.compose.runtime.Immutable


@Immutable
data class Chat(
    val chatId: String,
    val participantsIds: List<String>,
    val messages: List<Message>?
)
