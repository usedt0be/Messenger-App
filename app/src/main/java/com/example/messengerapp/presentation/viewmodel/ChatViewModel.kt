package com.example.messengerapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messengerapp.domain.models.Contact
import com.example.messengerapp.domain.usecases.GetChatDialogUseCase
import com.example.messengerapp.domain.usecases.GetContactByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class ChatViewModel @Inject constructor(
    private val getContactByIdUseCase: GetContactByIdUseCase,
    private val getChatDialogUseCase: GetChatDialogUseCase
): ViewModel() {

    private val _contact = MutableStateFlow<Contact?>(null)
    val contact
        get() = _contact.asStateFlow()


    
    fun getContact(contactId: String){
        viewModelScope.launch(Dispatchers.IO) {
            _contact.value = getContactByIdUseCase.invoke(contactId)
        }
    }

    fun getChatDialog(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getChatDialogUseCase.invoke(userId = userId)
        }
    }


}