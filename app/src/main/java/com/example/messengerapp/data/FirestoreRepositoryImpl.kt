package com.example.messengerapp.data

import android.util.Log
import com.example.messengerapp.data.firebase.UserEntity
import com.example.messengerapp.domain.FirestoreRepository
import com.example.messengerapp.util.ResultState
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirestoreRepositoryImpl(
    private val firestore: FirebaseFirestore
): FirestoreRepository {
    override fun insert(user: UserEntity): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading())
        firestore.collection("users")
            .add(user)
            .addOnSuccessListener {
                trySend(ResultState.Success("Data is inserted"))
            }
            .addOnFailureListener {
                trySend(ResultState.Error(it))
            }
        awaitClose {
            close()
        }
    }

    override fun getCurrentUserDetails(uid: String): Flow<ResultState<UserEntity>> = callbackFlow {
        trySend(ResultState.Loading())
        firestore.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener {
               val user = it.data
                Log.d("user", "$user")
            }
            .addOnFailureListener{
                    trySend(ResultState.Error(it))
            }
        awaitClose {
            close()
        }
    }

}