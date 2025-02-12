package com.example.messengerapp.domain.usecases

import android.provider.ContactsContract.Contacts
import com.example.messengerapp.domain.models.Contact
import com.example.messengerapp.domain.repository.UserStorageRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetContactsUseCase @Inject constructor(
    private val userStorageRepository: UserStorageRepository
) {
    val contactsFlow: StateFlow<List<Contact?>>
        get() = userStorageRepository.contactsFlow


    suspend operator fun invoke() = userStorageRepository.getContacts()
}