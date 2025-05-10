package com.example.messengerapp.data.repository

import android.util.Log
import com.example.messengerapp.core.storage.dao.ContactDao
import com.example.messengerapp.data.dto.ContactDto
import com.example.messengerapp.data.dto.UserDto
import com.example.messengerapp.data.mappers.toContact
import com.example.messengerapp.data.mappers.toContactEntity
import com.example.messengerapp.domain.models.Contact
import com.example.messengerapp.domain.repository.ContactsRepository
import com.example.messengerapp.util.ResultState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ContactsRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    private val contactsDao: ContactDao
): ContactsRepository {
    private val currentUserId = firebaseAuth.currentUser?.uid


    private val _contactsFlow = MutableStateFlow<List<Contact?>>(emptyList())
    override val contactsFlow: StateFlow<List<Contact?>>
        get() = _contactsFlow.asStateFlow()

    override fun addContact(firstName:String, secondName: String?, phoneNumber: String): Flow<ResultState<Contact>> = callbackFlow {
        trySend(ResultState.Loading())
        val query = firestore.collection("users")
            .whereEqualTo("phoneNumber", phoneNumber)
            .get()
            .await()

        Log.d("ErrorState_REPO", "${query.isEmpty}")

        if (!query.isEmpty) {
            val newUserDoc = query.documents.first().toObject(UserDto::class.java)

            val newContact = newUserDoc?.let { newUser ->
                firebaseAuth.currentUser?.uid?.let { userId ->
                    ContactDto(
                        id = newUser.userId!!,
                        ownerId = userId,
                        phoneNumber = newUser.phoneNumber!!,
                        firstName = firstName,
                        secondName = secondName,
                        photoUrl = newUser.imageUrl
                    )
                }

            }

            val currentUserId = firebaseAuth.currentUser?.uid

            val queryOfCurrentUser = currentUserId?.let {
                firestore.collection("users")
                    .whereEqualTo("userId", currentUserId)
                    .get()
                    .await()
            }

            val currentUserDocId = queryOfCurrentUser?.documents?.get(0)?.reference?.id

            Log.d("newContact", "$newContact")

            val currentUserDocReference = currentUserDocId?.let {
                firestore.collection("users").document(it)
            }
            currentUserDocReference?.update("contacts", FieldValue.arrayUnion(newContact))

            newContact?.let {
                trySend(ResultState.Success(newContact.toContact()))
            }
        } else {
            Log.d("ErrorState_REPO2", "INVOKED else")
            trySend(ResultState.Error(message = "User was not found"))
        }
        awaitClose {
            close()
        }
    }



    override suspend fun getContacts() {
        currentUserId?.let { it ->
            contactsDao.getContacts(ownerId = it).collect{ contactEntities ->
                Log.d("contacts_repo_get", "$contactEntities")
                val contacts = contactEntities.map {
                    it?.toContact()
                }
                _contactsFlow.value = contacts
            }
        }
    }

    override suspend fun getContactById(id: String): Contact? {
        val contact = contactsDao.getContactById(id)
        Log.d("contact_REPOSITORY", "$contact")
        return contact?.toContact()
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


    override fun getContactsFromFirebase(): Flow<List<Contact?>> = flow {
        val uid = firebaseAuth.currentUser?.uid
        Log.d("chat_USER", "$uid")
        uid?.let {
            val docSnapshot = firestore.collection("users")
                .whereEqualTo("userId", uid)
                .get()
                .await()
                .documents
                .first()
                .toObject(UserDto::class.java)
                ?.let { userDto ->
                    userDto.contacts.map { it?.toContact() }
                } ?: emptyList()

            emit(docSnapshot)

            Log.d("chat_user", "$docSnapshot")
        }
    }
}