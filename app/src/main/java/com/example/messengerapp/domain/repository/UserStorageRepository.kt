package com.example.messengerapp.domain.repository

import com.example.messengerapp.domain.models.Contact
import com.example.messengerapp.domain.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface UserStorageRepository {
    val userFlow: Flow<User?>

    val contactsFlow: StateFlow<List<Contact?>>

    suspend fun getContacts()

    suspend fun saveUserToDataStore(user: User)

    suspend fun deleteUserFromDataStore(user: User)

    suspend fun getUserFromDataStore()

    fun insertAllContactsToDb(contacts:List<Contact>?)

    fun insertContactToDb(contact: Contact)

    fun deleteContact(contact: Contact)

    suspend fun getContactById(id: String): Contact?
}