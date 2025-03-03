package com.example.messengerapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messengerapp.core.storage.token.TokensPersistence
import com.example.messengerapp.domain.models.Contact
import com.example.messengerapp.domain.repository.ContactsRepository
import com.example.messengerapp.domain.usecases.AddContactUseCase
import com.example.messengerapp.domain.usecases.DeleteContactUseCase
import com.example.messengerapp.domain.usecases.GetAuthTokenUseCase
import com.example.messengerapp.domain.usecases.GetChatDialogByContactIdUseCase
import com.example.messengerapp.domain.usecases.GetContactByIdUseCase
import com.example.messengerapp.domain.usecases.GetContactsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class ContactsViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository,
    private val getContactsUseCase: GetContactsUseCase,
    private val getContactByIdUseCase: GetContactByIdUseCase,
    private val addContactUseCase: AddContactUseCase,
    private val deleteContactUseCase: DeleteContactUseCase,
    private val getCurrentTokenUseCase: GetAuthTokenUseCase,
    private val getChatDialogByContactIdUseCase: GetChatDialogByContactIdUseCase,
    private val tokensPersistence: TokensPersistence
): ViewModel() {

    val contacts = getContactsUseCase.contactsFlow.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    private val _errorMessage = addContactUseCase.error
    val errorMessage = _errorMessage


    init {
        viewModelScope.launch {
            getContacts()
        }
    }

    private fun getContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            getContactsUseCase.invoke()
        }
    }

    fun addContact(phoneNumber: String, firstName: String, secondName: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            addContactUseCase.invoke(phoneNumber, firstName, secondName)
        }
    }


    fun deleteContact(contact: Contact){
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("deleting_contact", "$contact")
            deleteContactUseCase.invoke(contact)
        }
    }

    fun getChatDialogByContactId(contactId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val chat = getChatDialogByContactIdUseCase.invoke(userId = contactId)
            Log.d("chat_CONTACTS_VM", "$chat")
        }
    }

    fun getToken(){
        viewModelScope.launch {
            val token = tokensPersistence.getToken().firstOrNull()
            Log.d("AuthToken_VM", "$token")

        }
    }
}