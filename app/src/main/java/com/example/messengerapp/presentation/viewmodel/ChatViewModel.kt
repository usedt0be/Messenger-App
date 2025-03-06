package com.example.messengerapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messengerapp.core.viewmodel.factory.ViewModelAssistedFactory
import com.example.messengerapp.domain.models.Contact
import com.example.messengerapp.domain.usecases.GetChatDialogByContactIdUseCase
import com.example.messengerapp.domain.usecases.GetContactByIdUseCase
import com.example.messengerapp.domain.usecases.ObserveMessagesUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch



class ChatViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val getContactByIdUseCase: GetContactByIdUseCase,
    private val observeMessagesUseCase: ObserveMessagesUseCase,
    private val getChatDialogByContactIdUseCase: GetChatDialogByContactIdUseCase,
): ViewModel() {


    init {
        val id = savedStateHandle.get<String>("chat_participant_id")
        Log.d("Inited with id", "$id, ${this.hashCode()}")
    }

    private val _contact = MutableStateFlow<Contact?>(null)
    val contact
        get() = _contact.asStateFlow()


    fun getContact(contactId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _contact.value = getContactByIdUseCase.invoke(contactId)
        }
    }



    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<ChatViewModel>
}


