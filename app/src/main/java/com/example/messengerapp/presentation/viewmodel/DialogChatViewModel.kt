package com.example.messengerapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messengerapp.core.viewmodel.factory.ViewModelAssistedFactory
import com.example.messengerapp.domain.models.Contact
import com.example.messengerapp.domain.usecases.GetChatDialogByContactIdUseCase
import com.example.messengerapp.domain.usecases.GetContactByIdUseCase
import com.example.messengerapp.domain.usecases.InitMessagesSessionUseCase
import com.example.messengerapp.domain.usecases.ObserveMessagesUseCase
import com.example.messengerapp.domain.usecases.SendMessageUseCase
import com.example.messengerapp.util.ResultState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch



class DialogChatViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val getContactByIdUseCase: GetContactByIdUseCase,
    private val initMessagesSessionUseCase: InitMessagesSessionUseCase,
    private val observeMessagesUseCase: ObserveMessagesUseCase,
    private val getChatDialogByContactIdUseCase: GetChatDialogByContactIdUseCase,
    private val sendMessageUseCase: SendMessageUseCase
): ViewModel() {


    init {
        savedStateHandle.get<String>("chat_participant_id")?.let { participantId ->
            viewModelScope.launch(Dispatchers.IO) {
                getChatDialogByContactIdUseCase.invoke(participantId).let { chat ->
                    Log.d("chat_Info", "$chat")
                    val result = initMessagesSessionUseCase.invoke(chat.chatId)
                    Log.d("chat_status_res", "$result")
                    when (result) {
                        is ResultState.Success -> {
                            Log.d("chat_status", "success")
//                            observeMessagesUseCase.invoke(chat.chatId)
                        }

                        is ResultState.Error -> {
                            Log.d("chat_status", "pizdec ${result.message}")
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



    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<DialogChatViewModel>
}


