package com.example.messengerapp.domain.usecases

import android.provider.ContactsContract.Contacts
import com.example.messengerapp.domain.models.Contact
import com.example.messengerapp.domain.repository.ContactsRepository
import com.example.messengerapp.domain.repository.UserStorageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
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