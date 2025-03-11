package com.example.messengerapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messengerapp.core.viewmodel.factory.ViewModelAssistedFactory
import com.example.messengerapp.domain.models.Contact
import com.example.messengerapp.domain.models.Message
import com.example.messengerapp.domain.usecases.chat.CheckChatStatusUseCase
import com.example.messengerapp.domain.usecases.chat.DisconnectFromChatUseCase
import com.example.messengerapp.domain.usecases.chat.GetChatDialogByContactIdUseCase
import com.example.messengerapp.domain.usecases.chat.InitMessagesSessionUseCase
import com.example.messengerapp.domain.usecases.chat.ObserveMessagesUseCase
import com.example.messengerapp.domain.usecases.chat.SendMessageUseCase
import com.example.messengerapp.domain.usecases.contacts.GetContactByIdUseCase
import com.example.messengerapp.util.ResultState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class DialogChatViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val getContactByIdUseCase: GetContactByIdUseCase,
    private val initMessagesSessionUseCase: InitMessagesSessionUseCase,
    private val observeMessagesUseCase: ObserveMessagesUseCase,
    private val getChatDialogByContactIdUseCase: GetChatDialogByContactIdUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val disconnectFromChatUseCase: DisconnectFromChatUseCase,
    private val checkChatStatusUseCase: CheckChatStatusUseCase
): ViewModel() {

    private val _messages = MutableStateFlow<List<Message>?>(emptyList())
    val messages
        get() = _messages

    init {
        savedStateHandle.get<String>("chat_participant_id")?.let { participantId ->
            viewModelScope.launch(Dispatchers.IO) {
                getChatDialogByContactIdUseCase.invoke(participantId).let { chat ->
                    Log.d("chat_Info", "$chat")
                    val result = initMessagesSessionUseCase.invoke(chat.chatId)
                    Log.d("chat_status_res", "$result")
                    when (result) {
                        is ResultState.Success -> {
                            Log.d("chat_status_connection2", "${checkChatStatusUseCase.invoke()}")
                            delay(5000)
                            Log.d("chat_status_connection3", "${checkChatStatusUseCase.invoke()}")
                        }
                        is ResultState.Error -> {
                            Log.d("chat_status", "error ${result.message}")
                        }
                        is ResultState.Loading -> {}
                    }
                }
            }
        }
    }

    private val _contact = MutableStateFlow<Contact?>(null)
    val contact
        get() = _contact.asStateFlow()


    fun getContact(contactId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _contact.value = getContactByIdUseCase.invoke(contactId)
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

    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<DialogChatViewModel>
}


