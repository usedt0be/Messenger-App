package com.example.messengerapp.domain.repository

import com.example.messengerapp.UserProto
import com.example.messengerapp.data.entity.ContactEntity
import com.example.messengerapp.domain.models.Contact
import com.example.messengerapp.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserStorageRepository {
    suspend fun saveUserToDataStore(user: UserProto)

    fun getUserFromDataStore(): Flow<User?>

    fun insertAllContactsToDb(contacts:List<Contact>)

    fun getContactsFromDb(): List<Contact>

    fun insertContactToDb(contact: ContactEntity)
}