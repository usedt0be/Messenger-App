package com.example.messengerapp.domain.usecases.chat

import androidx.lifecycle.ViewModel
import com.example.messengerapp.domain.models.Message
import com.example.messengerapp.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveMessagesUseCase @Inject constructor(
    private val chatRepository: ChatRepository
): ViewModel() {

    operator fun invoke(chatId: String): Flow<Message> = chatRepository.observeChat(chatId)

}