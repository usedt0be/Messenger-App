package com.example.messengerapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messengerapp.domain.models.Contact
import com.example.messengerapp.domain.usecases.GetChatDialogByContactIdUseCase
import com.example.messengerapp.domain.usecases.GetContactByIdUseCase
import com.example.messengerapp.domain.usecases.ObserveMessagesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class ChatViewModel @Inject constructor(

    private val getContactByIdUseCase: GetContactByIdUseCase,
    private val observeMessagesUseCase: ObserveMessagesUseCase,
    private val getChatDialogByContactIdUseCase: GetChatDialogByContactIdUseCase,

): ViewModel() {

    private val _contact = MutableStateFlow<Contact?>(null)
    val contact
        get() = _contact.asStateFlow()

    
    fun getContact(contactId: String){
        viewModelScope.launch(Dispatchers.IO) {
            _contact.value = getContactByIdUseCase.invoke(contactId)
        }
    }


    init {

    }
//    fun getChatDialog(userId: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val chat = getChatDialogByContactIdUseCase.invoke(userId = userId)
//            Log.d("chat_VM", "$chat")
//        }
//    }


}