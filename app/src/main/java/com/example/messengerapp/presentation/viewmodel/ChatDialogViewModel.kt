package com.example.messengerapp.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messengerapp.core.viewmodel.factory.ViewModelAssistedFactory
import com.example.messengerapp.domain.models.Message
import com.example.messengerapp.domain.usecases.chat.CheckChatStatusUseCase
import com.example.messengerapp.domain.usecases.chat.DisconnectFromChatUseCase
import com.example.messengerapp.domain.usecases.chat.GetChatDialogByContactIdUseCase
import com.example.messengerapp.domain.usecases.chat.GetMessagesUseCase
import com.example.messengerapp.domain.usecases.chat.InitMessagesSessionUseCase
import com.example.messengerapp.domain.usecases.chat.ObserveMessagesUseCase
import com.example.messengerapp.domain.usecases.chat.SendMessageUseCase
import com.example.messengerapp.domain.usecases.contacts.GetContactByIdUseCase
import com.example.messengerapp.presentation.screens.chat.ChatDialogViewState
import com.example.messengerapp.util.ResultState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ChatDialogViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val getContactByIdUseCase: GetContactByIdUseCase,
    private val initMessagesSessionUseCase: InitMessagesSessionUseCase,
    private val observeMessagesUseCase: ObserveMessagesUseCase,
    private val getChatDialogByContactIdUseCase: GetChatDialogByContactIdUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val disconnectFromChatUseCase: DisconnectFromChatUseCase,
    private val checkChatStatusUseCase: CheckChatStatusUseCase,
    private val getMessagesUseCase: GetMessagesUseCase
): ViewModel() {

    var state by mutableStateOf(ChatDialogViewState())
        private set

    init {
        savedStateHandle.get<String>("chat_participant_id")?.let { participantId ->
            viewModelScope.launch(Dispatchers.IO) {
                getChatDialogByContactIdUseCase.invoke(participantId).let { chat ->
                    Log.d("chat_Info", "$chat")
                    val messages = getMessagesUseCase.invoke(chatId = chat.chatId , page = state.messagesPage)
                    Log.d("chat_first_messages", "$messages")
                    withContext(Dispatchers.Main) {
                        state = state.copy(
                            messages = messages,
                            messagesPage = state.messagesPage + 1,
                            chat = chat
                        )
                    }
                    val initChatSessionResult = initMessagesSessionUseCase.invoke(chat.chatId)
                    Log.d("chat_status_res", "$initChatSessionResult")
                    val messagesList: MutableList<Message> = state.messages.toMutableList()
                    when (initChatSessionResult) {
                        is ResultState.Success -> {
                            Log.d("chat_status_connection1", "${checkChatStatusUseCase.invoke()}")
                            observeMessagesUseCase.invoke().collect { message ->
                                Log.d("chat_message_VM", "$message")
                                messagesList.add(0, message)
                                Log.d("chat_message_VM2", "$messagesList")
                                state = state.copy(messages = messagesList)
                            }
                            Log.d("chat_status_connection2", "${checkChatStatusUseCase.invoke()}")
                        }
                        is ResultState.Error -> {
                            Log.d("chat_status", "error ${initChatSessionResult.message}")
                        }
                        is ResultState.Loading -> {

                        }
                    }
                }
            }
        }
    }


    fun getContact(contactId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val contact = getContactByIdUseCase.invoke(contactId)
            withContext(Dispatchers.Main) {
                state = state.copy(
                    chatParticipantContact = contact
                )
            }
        }
    }

    fun getNextMessagesPage() {
        viewModelScope.launch(Dispatchers.IO) {
            state.chat?.let {
                val newMessages = getMessagesUseCase(chatId = it.chatId, page = state.messagesPage)
                withContext(Dispatchers.Main) {
                    state = state.copy(
                        messages = state.messages + newMessages,
                        messagesPage = state.messagesPage + 1
                    )
                }
            }
        }
    }

    fun sendMessage(text: String){
        viewModelScope.launch(Dispatchers.IO) {
            sendMessageUseCase.invoke(text = text)
        }
    }

    fun disconnect(){
        viewModelScope.launch(Dispatchers.IO) {
            disconnectFromChatUseCase.invoke()
        }
    }

    override fun onCleared() {
        Log.d("CHAT_VM_CLEARED", "INVOKED")
    }
    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<ChatDialogViewModel>
}


