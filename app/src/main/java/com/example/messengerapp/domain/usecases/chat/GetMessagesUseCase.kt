package com.example.messengerapp.domain.usecases.chat

import com.example.messengerapp.domain.models.Message
import com.example.messengerapp.domain.repository.ChatRepository
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(
    private val chatRepositoryImpl: ChatRepository
) {

    suspend operator fun invoke(chatId: String, page: Int): List<Message> =
        chatRepositoryImpl.getMessages(chatId = chatId, page = page)

}