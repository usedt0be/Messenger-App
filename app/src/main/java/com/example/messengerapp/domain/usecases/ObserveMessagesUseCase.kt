package com.example.messengerapp.domain.usecases

import androidx.lifecycle.ViewModel
import com.example.messengerapp.domain.repository.ChatRepository
import javax.inject.Inject

class ObserveMessagesUseCase @Inject constructor(
    private val chatRepository: ChatRepository
): ViewModel() {

    suspend operator fun invoke(chatId: String ){
        chatRepository.observeChat(chatId)
    }
}