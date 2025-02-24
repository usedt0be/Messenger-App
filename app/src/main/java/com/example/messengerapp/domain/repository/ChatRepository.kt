package com.example.messengerapp.domain.repository

import com.example.messengerapp.domain.models.Message
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    suspend fun observeChat(chatId: String): Flow<Message>

    suspend fun sendMessage(text: String)


    suspend fun getChatDialog(ownId: String, userId: String)

}