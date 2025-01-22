package com.example.messengerapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messengerapp.data.entity.UserEntity
import com.example.messengerapp.domain.ContactsRepository
import com.example.messengerapp.util.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ContactsViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository
): ViewModel() {

    private val _contact = MutableStateFlow<UserEntity?>(null)
    val contact = _contact.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    suspend fun getContact(phoneNumber: String) {
        viewModelScope.launch(Dispatchers.IO) {
            contactsRepository.addContact(phoneNumber).collect {userResultState ->
                when(userResultState) {
                    is ResultState.Loading -> {

                    }
                    is ResultState.Success -> {
                        _contact.value = userResultState.data
                    }
                    is ResultState.Error -> {
                       _errorMessage.value = userResultState.message
                    }
                }
            }
        }
    }



}