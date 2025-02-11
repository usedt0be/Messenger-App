package com.example.messengerapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messengerapp.data.dto.UserDto
import com.example.messengerapp.domain.models.Contact
import com.example.messengerapp.domain.repository.ContactsRepository
import com.example.messengerapp.domain.usecases.AddContactUseCase
import com.example.messengerapp.domain.usecases.DeleteContactUseCase
import com.example.messengerapp.domain.usecases.GetContactByIdUseCase
import com.example.messengerapp.domain.usecases.GetContactsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ContactsViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository,
    private val getContactsUseCase: GetContactsUseCase,
    private val getContactByIdUseCase: GetContactByIdUseCase,
    private val addContactUseCase: AddContactUseCase,
    private val deleteContactUseCase: DeleteContactUseCase,
): ViewModel() {

    private val _contact = MutableStateFlow<UserDto?>(null)
    val contact = _contact.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    private val _contacts = MutableStateFlow<List<Contact>?>(null)
    val contacts = _contacts.asStateFlow()

    init {

        Log.d("contactsVM", "INIT")
        viewModelScope.launch(Dispatchers.IO) {
            _contacts.value = getContactsUseCase.invoke()
        }
        Log.d("contactsVM.value", "$contacts")
    }

    fun addContact(phoneNumber: String, firstName: String, secondName: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            addContactUseCase.invoke(phoneNumber, firstName, secondName)
        }
    }


    fun deleteContact(contact: Contact){
        viewModelScope.launch(Dispatchers.IO) {
            deleteContactUseCase.invoke(contact)
        }
    }
}