package com.example.messengerapp.domain.usecases.contacts

import com.example.messengerapp.domain.repository.ContactsRepository
import javax.inject.Inject

class GetContactsFromFirebaseUseCase @Inject constructor(
    private val contactsRepository: ContactsRepository
) {
    operator fun invoke() =
        contactsRepository.getContactsFromFirebase()

}