package com.example.messengerapp.presentation.screens.chats

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messengerapp.data.mappers.toChatParticipant
import com.example.messengerapp.domain.models.Chat
import com.example.messengerapp.domain.models.ChatParticipant
import com.example.messengerapp.domain.usecases.chat.GetChatParticipantsUseCase
import com.example.messengerapp.domain.usecases.chat.GetChatsUseCase
import com.example.messengerapp.domain.usecases.contacts.GetContactsFromFirebaseUseCase
import com.example.messengerapp.util.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChatsViewModel @Inject constructor(
    private val getChatsUseCase: GetChatsUseCase,
    private val getChatParticipantsUseCase: GetChatParticipantsUseCase,
    private val getContactsFromFirebaseUseCase: GetContactsFromFirebaseUseCase,
): ViewModel() {

    var state by mutableStateOf(ChatsViewState())
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val chats = getChatsUseCase.invoke()
            val contacts = getContactsFromFirebaseUseCase.invoke().firstOrNull()

            val chatState: List<Pair<Chat, ChatParticipant?>> = chats.map { chat ->
                async {
                    val participantId = chat.participantsIds.firstOrNull()
                    var chatParticipant: ChatParticipant? = null

                    if (participantId != null) {
                        getChatParticipantsUseCase.invoke(participantId = participantId).collect{ chatParticipantState ->
                            when(chatParticipantState) {
                                is ResultState.Success -> {
                                    val contactChatParticipant = chatParticipantState.data?.let { chatParticipant ->
                                        contacts?.find { it?.id?.value == chatParticipant.id }
                                    }?.toChatParticipant() ?: run {
                                        null
                                    }
                                    Log.d("chat_participanT", "$contactChatParticipant")

                                    chatParticipant = if (contactChatParticipant !== null) {
                                        contactChatParticipant
                                    } else {
                                        chatParticipantState.data
                                    }
                                }

                                is ResultState.Loading -> {
                                    Log.d("chats_STATE", "Loading")
                                }

                                is ResultState.Error -> {

                                }
                            }
                        }
                        Pair(first = chat, second = chatParticipant)
                    } else {
                        Pair(first = chat, second = chatParticipant)
                    }
                }
            }.awaitAll()


            Log.d("chat_participantSS", "${chatState}")
            state = state.copy(chats = chatState)

        }
    }



}