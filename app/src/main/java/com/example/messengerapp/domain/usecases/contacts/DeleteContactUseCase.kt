package com.example.messengerapp.domain.usecases.contacts

import com.example.messengerapp.domain.models.Contact
import com.example.messengerapp.domain.repository.ContactsRepository
import javax.inject.Inject

class DeleteContactUseCase @Inject constructor(
    private val contactsRepository: ContactsRepository
) {
    operator fun invoke(contact: Contact){
        contactsRepository.deleteContact(contact)
    }
}