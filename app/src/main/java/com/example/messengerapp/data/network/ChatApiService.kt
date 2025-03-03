package com.example.messengerapp.data.network

import com.example.messengerapp.data.dto.ChatDto
import com.example.messengerapp.data.dto.MessageDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ChatApiService {

    @GET("chats/dialog/{userId}")  //gets chat by CONTACT_id
    suspend fun getDialogChat(
        @Path("userId") dialogUserId: String
    ): ChatDto

    @GET("chats/{id}/messages")
    suspend fun getMessagesForChat(
        @Header("Authorization") token: String,
        @Path("id") chatId: String
    ): List<MessageDto>


    @DELETE("chats/{id}")
    suspend fun deleteChat(
        @Header("Authorization") token: String,
        @Path("id") chatId: String
    )

}