package com.example.messengerapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messengerapp.data.dto.UserDto
import com.example.messengerapp.domain.repository.ContactsRepository
import com.example.messengerapp.util.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ContactsViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository
): ViewModel() {

    private val _contact = MutableStateFlow<UserDto?>(null)
    val contact = _contact.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    fun addContact(firstName: String, secondName: String?, phoneNumber: String) {
        viewModelScope.launch(Dispatchers.IO) {
            contactsRepository.addContact(
                firstName = firstName,
                secondName = secondName,
                phoneNumber = phoneNumber
            ).collect{ resultState ->
                    when(resultState) {
                        is ResultState.Loading -> {}
                        is ResultState.Success -> {}
                        is ResultState.Error -> {}
                    }
            }
        }
    }

}