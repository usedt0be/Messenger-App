package com.example.messengerapp.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import com.example.messengerapp.UserProto
import com.example.messengerapp.core.storage.dao.ContactDao
import com.example.messengerapp.data.mappers.toContact
import com.example.messengerapp.data.mappers.toContactEntity
import com.example.messengerapp.data.mappers.toUser
import com.example.messengerapp.data.mappers.toUserProto
import com.example.messengerapp.domain.models.Contact
import com.example.messengerapp.domain.models.User
import com.example.messengerapp.domain.repository.UserStorageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class UserStorageRepositoryImpl @Inject constructor(
    private val userDataStore: DataStore<UserProto?>,
    private val contactsDao: ContactDao
): UserStorageRepository {

    private val _userFlow = MutableStateFlow<User?>(value = null)
    override val userFlow: Flow<User?>
        get() = _userFlow

    private val _contactsFlow = MutableStateFlow<List<Contact?>>(emptyList())

    override val contactsFlow: StateFlow<List<Contact?>>
        get() = _contactsFlow.asStateFlow()



    override suspend fun getContacts() {
        contactsDao.getContacts().collect{ contactEntities ->
            Log.d("contacts_repo_get", "$contactEntities")
            val contacts = contactEntities.map {
                it.toContact()
            }
            _contactsFlow.value = contacts
        }
    }

    override suspend fun saveUserToDataStore(user: User) {
        Log.d("UseSavedTODataStore", "$user")
        userDataStore.updateData { user.toUserProto() }
        getUserFromDataStore()
    }

    override suspend fun deleteUserFromDataStore(user: User) {

    }


    override suspend fun getUserFromDataStore() {
        userDataStore.data.collectLatest { it ->
            val user = it.toUser()
            Log.d("user_FROM_DS", "$user")
            _userFlow.value = user
            Log.d("user_FROM_DS", "${_userFlow.value}")
        }
    }

    override fun insertAllContactsToDb(contacts:List<Contact>?) {
        contacts?.map { contactDto ->
            contactDto.toContactEntity()
        }?.let {
            contactsDao.upsertAllContactsToDb(it)
        }
    }


    override fun insertContactToDb(contact: Contact) {
        contactsDao.upsertUser(contact = contact.toContactEntity())
    }

    override fun deleteContact(contact: Contact) {
        contactsDao.deleteContact(contact = contact.toContactEntity())
    }

    override suspend fun getContactById(id: String): Contact? {
        val contact = contactsDao.getContactById(id)
        Log.d("contact_REPOSITORY", "$contact")
        return contact?.toContact()
    }
}