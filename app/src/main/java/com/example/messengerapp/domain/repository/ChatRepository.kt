package com.example.messengerapp.domain.repository

import com.example.messengerapp.domain.models.Chat
import com.example.messengerapp.domain.models.ChatParticipant
import com.example.messengerapp.domain.models.Message
import com.example.messengerapp.domain.models.User
import com.example.messengerapp.util.ResultState
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun initMessagesSession(chatId: String): ResultState<Unit>

    fun observeChat(): Flow<Message>

    suspend fun sendMessage(text: String)

    suspend fun getChatDialog(userId: String): Chat

    suspend fun getChatsForUser(): List<Chat>

    suspend fun closeMessageSessionConnection()

    suspend fun checkSessionState(): Boolean?

    suspend fun observeSession()

    suspend fun getMessages(chatId: String, page: Int): List<Message>

    fun getChatParticipant(participantId: String): Flow<ResultState<ChatParticipant>>
}