package com.example.messengerapp.presentation.screens.chat_dialog

import com.example.messengerapp.domain.models.Chat
import com.example.messengerapp.domain.models.Contact
import com.example.messengerapp.domain.models.Message

data class ChatDialogViewState(
    val messages: List<Message> = emptyList(),
    val chat: Chat? = null,
    val chatParticipantContact: Contact? = null,
    val messagesPage: Int = 1,
    val isLoading: Boolean = false,
    val loadingNexMessages: Boolean = false,
)