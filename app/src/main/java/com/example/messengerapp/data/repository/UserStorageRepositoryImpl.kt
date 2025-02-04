package com.example.messengerapp.data.repository

import android.provider.ContactsContract.Contacts
import androidx.datastore.core.DataStore
import com.example.messengerapp.UserProto
import com.example.messengerapp.data.dao.ContactDao
import com.example.messengerapp.data.dto.ContactDto
import com.example.messengerapp.data.dto.UserDto
import com.example.messengerapp.data.entity.ContactEntity
import com.example.messengerapp.data.mappers.toContact
import com.example.messengerapp.data.mappers.toContactEntity
import com.example.messengerapp.data.mappers.toUser
import com.example.messengerapp.data.mappers.toUserProto
import com.example.messengerapp.domain.models.Contact
import com.example.messengerapp.domain.models.User
import com.example.messengerapp.domain.repository.UserStorageRepository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserStorageRepositoryImpl @Inject constructor(
    private val userDataStore: DataStore<UserProto?>,
    private val contactsDao: ContactDao
): UserStorageRepository {


    override suspend fun saveUserToDataStore(user: UserProto) {
        userDataStore.updateData { user }
    }

    override fun getUserFromDataStore(): Flow<User?> {
        return userDataStore.data.map { it.toUser()}
    }


    override fun insertAllContactsToDb(contacts:List<ContactDto>) {
        val contactEntities = contacts.map { contactDto ->
            contactDto.toContactEntity()
        }
        contactsDao.upsertAllContactsToDb(contacts = contactEntities)
    }


    override fun getContactsFromDb(): List<Contact> {
       return contactsDao.getContacts().map { contactEntity ->
           contactEntity.toContact()
       }
    }

    override fun insertContactToDb(contact: ContactEntity) {
        contactsDao.upsertUser(contact)
    }

}