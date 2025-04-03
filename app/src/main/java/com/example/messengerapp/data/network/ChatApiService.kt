package com.example.messengerapp.data.network

import com.example.messengerapp.data.dto.ChatDto
import com.example.messengerapp.data.dto.MessageDto
import com.example.messengerapp.data.dto.ResponseMeta
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ChatApiService {
    @GET("chats")
    suspend fun getChatsForUser(): ResponseMeta<List<ChatDto>>

    @GET("chats/dialog/{userId}")  //gets chat by CONTACT_id
    suspend fun getDialogChat(
        @Path("userId") dialogUserId: String
    ): ResponseMeta<ChatDto>

    @GET("chats/{chatId}/messages")
    suspend fun getMessagesForChat(
        @Path("chatId") chatId: String
    ): ResponseMeta<List<MessageDto>>


    @DELETE("chats/{id}")
    suspend fun deleteChat(
        @Path("id") chatId: String
    )


    companion object {
        val BASE_SOCKET_URL = "ws://10.0.2.2:8080"
    }

}