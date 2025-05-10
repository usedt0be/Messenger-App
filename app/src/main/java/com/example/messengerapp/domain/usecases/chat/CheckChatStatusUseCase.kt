package com.example.messengerapp.domain.usecases.chat

import com.example.messengerapp.domain.repository.ChatRepository
import javax.inject.Inject

class CheckChatStatusUseCase @Inject constructor(
    private val repo: ChatRepository
) {
    suspend operator fun invoke(): Boolean?{
        return repo.checkSessionState()
    }
}