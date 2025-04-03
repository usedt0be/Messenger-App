package com.example.messengerapp.domain.usecases.chat

import com.example.messengerapp.domain.models.Chat
import com.example.messengerapp.domain.repository.ChatRepository
import javax.inject.Inject


class GetChatDialogByContactIdUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(userId: String): Chat {
        return chatRepository.getChatDialog(userId = userId)
    }

}