package com.example.messengerapp.domain.usecases.contacts

import com.example.messengerapp.domain.models.Contact
import com.example.messengerapp.domain.repository.ContactsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetContactsUseCase @Inject constructor(
    private val contactsRepository: ContactsRepository
) {
    val contactsFlow: Flow<List<Contact?>>
        get() = contactsRepository.contactsFlow


    suspend operator fun invoke() {
        contactsRepository.getContacts()
    }
}