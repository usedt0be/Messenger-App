package com.example.messengerapp.domain.usecases

import com.example.messengerapp.domain.models.Contact
import com.example.messengerapp.domain.repository.ContactsRepository
import com.example.messengerapp.domain.repository.UserStorageRepository
import javax.inject.Inject

class DeleteContactUseCase @Inject constructor(
    private val contactsRepository: ContactsRepository
) {
    operator fun invoke(contact: Contact){
        contactsRepository.deleteContact(contact)
    }
}