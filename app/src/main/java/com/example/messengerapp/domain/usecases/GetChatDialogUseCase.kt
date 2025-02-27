package com.example.messengerapp.domain.usecases

import com.example.messengerapp.domain.repository.ChatRepository
import javax.inject.Inject


class GetChatDialogUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(userId: String){
        chatRepository.getChatDialog(userId = userId)
    }

}