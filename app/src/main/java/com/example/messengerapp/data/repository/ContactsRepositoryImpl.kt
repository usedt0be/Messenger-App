package com.example.messengerapp.data.repository

import android.util.Log
import com.example.messengerapp.data.dto.ContactDto
import com.example.messengerapp.data.dto.UserDto
import com.example.messengerapp.domain.repository.ContactsRepository
import com.example.messengerapp.util.ResultState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ContactsRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
): ContactsRepository {

    override fun addContact(firstName:String, secondName: String?, phoneNumber: String): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading())

        val query = firestore.collection("users")
            .whereEqualTo("phoneNumber", phoneNumber)
            .get()
            .await()

        if (!query.isEmpty) {
            val newUserDoc = query.documents.first().toObject(UserDto::class.java)

            val newContact = newUserDoc?.let { newUser ->
                ContactDto(
                    id = newUser.userId,
                    phoneNumber = newUser.phoneNumber,
                    firstName = firstName,
                    secondName = secondName,
                    photoUrl = newUser.imageUrl
                )
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

            trySend(ResultState.Success(newContact.toString()))
        } else {
            trySend(ResultState.Error(message = "user was not found"))
        }
        awaitClose {
            close()
        }
    }
}