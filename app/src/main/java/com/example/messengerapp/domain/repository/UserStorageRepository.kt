package com.example.messengerapp.domain.repository

import com.example.messengerapp.domain.models.Contact
import com.example.messengerapp.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserStorageRepository {
    val userFlow: Flow<User?>

    suspend fun saveUserToDataStore(user: User)

    suspend fun getUserFromDataStore()

    fun insertAllContactsToDb(contacts:List<Contact>?)

    fun getContactsFromDb(): List<Contact>

    fun insertContactToDb(contact: Contact)
}