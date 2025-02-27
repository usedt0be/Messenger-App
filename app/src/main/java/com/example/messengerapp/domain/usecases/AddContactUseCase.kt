package com.example.messengerapp.domain.usecases

import android.util.Log
import com.example.messengerapp.domain.repository.ContactsRepository
import com.example.messengerapp.domain.repository.UserStorageRepository
import com.example.messengerapp.util.ResultState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class AddContactUseCase @Inject constructor(
    private val contactsRepository: ContactsRepository,
) {
    val error = MutableSharedFlow<String?>()

    suspend operator fun invoke(phoneNumber: String, firstName: String, secondName: String?) {
        contactsRepository.addContact(
            phoneNumber = phoneNumber,
            firstName = firstName,
            secondName = secondName
        ).collect { result ->
            when(result) {
                is ResultState.Success -> {
                    result.data?.let { contact ->
                        contactsRepository.insertContactToDb(contact = contact)
                    }
                }
                is ResultState.Loading -> {

                }
                is ResultState.Error -> {
                    error.emit(result.message)
                }
            }
        }
    }
}