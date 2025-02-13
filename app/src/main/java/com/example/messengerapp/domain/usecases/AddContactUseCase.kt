package com.example.messengerapp.domain.usecases

import com.example.messengerapp.domain.repository.ContactsRepository
import com.example.messengerapp.domain.repository.UserStorageRepository
import com.example.messengerapp.util.ResultState
import javax.inject.Inject

class AddContactUseCase @Inject constructor(
    private val contactsRepository: ContactsRepository,
    private val userStorageRepository: UserStorageRepository
) {

    suspend operator fun invoke(phoneNumber: String, firstName: String, secondName: String?) {
        contactsRepository.addContact(
            phoneNumber = phoneNumber,
            firstName = firstName,
            secondName = secondName
        ).collect { result ->
            when(result) {
                is ResultState.Success -> {
                    result.data?.let { contact ->
                        userStorageRepository.insertContactToDb(contact = contact)
                    }
                }
                is ResultState.Loading -> {}
                is ResultState.Error -> {}
            }
        }

    }
}