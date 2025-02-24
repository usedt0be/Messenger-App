package com.example.messengerapp.data.repository

import com.example.messengerapp.data.dto.MessageDto
import com.example.messengerapp.data.network.ChatApiService
import com.example.messengerapp.domain.models.Message
import com.example.messengerapp.domain.repository.ChatRepository
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatApi: ChatApiService
):ChatRepository{
    private var chatsSocketSession: WebSocketSession? = null
    private var messagesSocketSession: WebSocketSession? = null


    override suspend fun observeChat(chatId: String): Flow<Message>{
        return try {
            flow {
            messagesSocketSession?.incoming
                ?.receiveAsFlow()
                ?.filterIsInstance<Frame.Text>()
                ?.map { it ->
                    val stringFrame = it.readText()
                    val message = Json.decodeFromString<MessageDto>(stringFrame)

                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            flow {  }
        }
    }

    override suspend fun sendMessage(text: String) {
        try {
            messagesSocketSession?.send(
                Frame.Text(text = text)
            )
        } catch (
            e:Exception
        ) {
            e.printStackTrace()
        }
    }

    override suspend fun getChatDialog(ownId: String, userId: String) {
        TODO("Not yet implemented")
    }



}