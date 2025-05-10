package com.example.messengerapp.presentation.screens.chats

import com.example.messengerapp.domain.models.Chat
import com.example.messengerapp.domain.models.ChatParticipant
import com.example.messengerapp.domain.models.Message

data class ChatsViewState(
    val chats: List<Pair<Chat, ChatParticipant?>> = emptyList()
)