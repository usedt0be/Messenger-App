package com.example.messengerapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messengerapp.domain.models.Contact
import com.example.messengerapp.domain.usecases.GetContactByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class ChatViewModel @Inject constructor(
    private val getContactByIdUseCase: GetContactByIdUseCase,

): ViewModel() {

    private val _contact = MutableStateFlow<Contact?>(null)
    val contact
        get() = _contact.asStateFlow()


    
    fun getContact(contactId: String){
        viewModelScope.launch(Dispatchers.IO) {
            _contact.value = getContactByIdUseCase.invoke(contactId)
        }
    }

//    fun getChatDialog(userId: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val chat = getChatDialogUseCase.invoke(userId = userId)
//            Log.d("chat_VM", "$chat")
//        }
//    }


}