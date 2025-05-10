package com.example.messengerapp.domain.usecases.chat

import android.util.Log
import com.example.messengerapp.domain.models.ChatParticipant
import com.example.messengerapp.domain.repository.ChatRepository
import com.example.messengerapp.domain.repository.ContactsRepository
import com.example.messengerapp.util.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class GetChatParticipantsUseCase @Inject constructor(
    private val chatRepository: ChatRepository,
    private val contactsRepository: ContactsRepository
) {
    suspend fun invoke(participantId: String): Flow<ResultState<ChatParticipant>> {
        return chatRepository.getChatParticipant(participantId = participantId)
    }

}