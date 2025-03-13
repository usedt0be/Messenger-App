package com.example.messengerapp.data.repository

import android.util.Log
import com.example.messengerapp.core.storage.token.TokensPersistence
import com.example.messengerapp.data.dto.MessageDto
import com.example.messengerapp.data.mappers.toChat
import com.example.messengerapp.data.mappers.toMessage
import com.example.messengerapp.data.network.ChatApiService
import com.example.messengerapp.domain.models.Chat
import com.example.messengerapp.domain.models.Message
import com.example.messengerapp.domain.repository.ChatRepository
import com.example.messengerapp.util.ResultState
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.FrameType
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatApi: ChatApiService,
    private val httpClient: HttpClient,
    private val tokensPersistence: TokensPersistence
):ChatRepository{


    private var chatsSocketSession: WebSocketSession? = null
    private var messagesSocketSession: WebSocketSession? = null

    private val token = runBlocking { tokensPersistence.getToken().firstOrNull() }

    override suspend fun initMessagesSession(chatId: String): ResultState<Unit> {
        return try {
            Log.d("chat_websocket_token", "${token?.value}")
            messagesSocketSession = httpClient.webSocketSession {
                headers.append(name = HttpHeaders.Authorization, value = "Bearer ${token?.value}")
                url("ws://10.0.2.2:8080/chats/$chatId/ws")
            }
            if(messagesSocketSession?.isActive == true) {
//                Log.d("chat_SESSION", "${messagesSocketSession.toString()}")
                ResultState.Success(Unit)
            }else {
                ResultState.Error(message = "socket is not active")
            }
        } catch (e: Exception) {
            ResultState.Error(message = e.toString())
        }
    }

    override fun observeChat(): Flow<Message> {
        return try {
                messagesSocketSession?.incoming
                    ?.receiveAsFlow()
                    ?.filter { frame ->  frame is Frame.Text }
                    ?.map { frame ->
                        Log.d("chat_FRAME_map", "text: $frame")
                        val frameString = (frame as? Frame.Text)?.readText() ?: ""
                        val messageDto = Json.decodeFromString<MessageDto>(frameString)
                        Log.d("chat_DTO", "text: $messageDto")
                        messageDto.toMessage()
                    }
                    ?: flow {  }
        } catch (e: Exception) {
            e.printStackTrace()
            flow { }
        }
    }

    override suspend fun sendMessage(text: String) {
        Log.d("message_repo", "text: $text")
        Log.d("message_repo_session", "${messagesSocketSession?.isActive}")
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

    override suspend fun getChatDialog(userId: String): Chat {
        return chatApi.getDialogChat(dialogUserId = userId).data.toChat()
    }

    override suspend fun getChatsForUser(userId: String): List<Chat> {
        val chats = chatApi.getChatsForUser().data.map { it.toChat() }
        Log.d("chats_get", "$chats")
        return chats
    }

    override suspend fun closeMessageSessionConnection() {
        messagesSocketSession?.close(CloseReason(code = CloseReason.Codes.NORMAL, "Session closed"))
        messagesSocketSession = null
    }

    override suspend fun checkSessionState(): Boolean? {
        val res = messagesSocketSession?.isActive
        return res
    }
    suspend fun observeSessions() {
        Log.d("chat_observer", "invoked" )

            val d = messagesSocketSession?.incoming?.receive()
                ?.data

    }
    override suspend fun observeSession() {
        messagesSocketSession?.incoming?.consumeEach { frame ->
            when(frame) {
                is Frame.Text -> {
                    Log.d("chat_websocket_frame", "text ${frame.readText()}")
                }
                is Frame.Binary -> Log.d("chat_websocket_frame", "Received binary data")
                is Frame.Ping -> Log.d("chat_websocket_frame", "Received PING")
                is Frame.Pong -> Log.d("chat_websocket_frame", "Received PONG")
                is Frame.Close -> Log.d("chat_websocket_frame", "WebSocket closed")
            }
        }
  }


}