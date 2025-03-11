package com.example.messengerapp.domain.usecases.chat



import com.example.messengerapp.domain.repository.ChatRepository
import com.example.messengerapp.util.ResultState
import javax.inject.Inject

class InitMessagesSessionUseCase @Inject constructor(
    private val chatRepository: ChatRepository,
) {
    suspend operator fun invoke(chatId: String): ResultState<Unit> {
        return chatRepository.initMessagesSession(chatId)
    }
}