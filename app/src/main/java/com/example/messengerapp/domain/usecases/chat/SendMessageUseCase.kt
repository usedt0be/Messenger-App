package com.example.messengerapp.domain.usecases.chat

import com.example.messengerapp.domain.repository.ChatRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {

    suspend operator fun invoke(text: String){
        chatRepository.sendMessage(text = text)
    }
}