package com.example.messengerapp.data.repository

import android.util.Log
import com.example.messengerapp.data.entity.UserEntity
import com.example.messengerapp.domain.ContactsRepository
import com.example.messengerapp.util.ResultState
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ContactsRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): ContactsRepository {
    override fun addContact(phoneNumber: String): Flow<ResultState<UserEntity>> = callbackFlow {
        Log.d("contactsNumber", phoneNumber)

        trySend(ResultState.Loading())

        val query = firestore.collection("users")
            .whereEqualTo("PhoneNumber", phoneNumber)
            .get()

        query.addOnSuccessListener { querySnapshot ->
            val userDocument = querySnapshot.documents.first()
            val user = userDocument.toObject(UserEntity::class.java)

            Log.d("contact_user", "$user")

            if (user != null ) {
                trySend(ResultState.Success(data = user))
            }
        }.addOnFailureListener{
            trySend(ResultState.Error(message = "User was not found"))
        }
        awaitClose {
            close()
        }
    }
}