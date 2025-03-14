package com.example.messengerapp.domain.usecases.chat

import com.example.messengerapp.domain.repository.ChatRepository
import javax.inject.Inject

class DisconnectFromChatUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {

    suspend operator fun invoke(){
        chatRepository.closeMessageSessionConnection()
    }
}