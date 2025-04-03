package com.example.messengerapp.domain.repository

import com.example.messengerapp.domain.models.Contact
import com.example.messengerapp.util.ResultState
import kotlinx.coroutines.flow.Flow

interface ContactsRepository {
    val contactsFlow: Flow<List<Contact?>>

    fun addContact(firstName:String, secondName: String?, phoneNumber: String): Flow<ResultState<Contact>>

    suspend fun getContacts()

    suspend fun getContactById(id: String): Contact?

    fun deleteContact(contact: Contact)

    fun insertAllContactsToDb(contacts:List<Contact>?)

    fun insertContactToDb(contact: Contact)

}