package com.example.messengerapp.presentation.screens.chats

import com.example.messengerapp.domain.models.Chat
import com.example.messengerapp.domain.models.Message

data class ChatsViewState(
    val chats: List<Map<Chat, Message>> = emptyList()
)