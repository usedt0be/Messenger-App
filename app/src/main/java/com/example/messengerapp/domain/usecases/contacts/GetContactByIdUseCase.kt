package com.example.messengerapp.domain.usecases.contacts

import android.util.Log
import com.example.messengerapp.domain.models.Contact
import com.example.messengerapp.domain.repository.ContactsRepository
import javax.inject.Inject

class GetContactByIdUseCase @Inject constructor(
    private val contactsRepository: ContactsRepository
) {
    suspend operator fun invoke(contactId: String): Contact? {
        Log.d("use_case_id", "$contactId")
       return contactsRepository.getContactById(contactId)
    }
}