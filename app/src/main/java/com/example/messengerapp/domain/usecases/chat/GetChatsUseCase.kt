package com.example.messengerapp.domain.usecases.chat

import com.example.messengerapp.domain.repository.ChatRepository
import javax.inject.Inject


class GetChatsUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(userId: String) =
        chatRepository.getChatsForUser(userId)

}