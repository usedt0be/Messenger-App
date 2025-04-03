package com.example.messengerapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messengerapp.domain.models.Contact
import com.example.messengerapp.domain.usecases.contacts.GetContactByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class ContactDetailsViewModel @Inject constructor(
    private val getContactByIdUseCase: GetContactByIdUseCase
): ViewModel() {

    private val _contact = MutableStateFlow<Contact?>(null)
    val contact
        get() = _contact.asStateFlow()


    fun getUser(contactId: Contact.Id){
        viewModelScope.launch(Dispatchers.IO) {
            _contact.value = getContactByIdUseCase.invoke(contactId = contactId.value)
        }
    }
}