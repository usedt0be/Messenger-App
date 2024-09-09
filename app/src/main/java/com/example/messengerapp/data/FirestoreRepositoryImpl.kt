package com.example.messengerapp.data

import android.util.Log
import com.example.messengerapp.data.firebase.UserEntity
import com.example.messengerapp.domain.FirestoreRepository
import com.example.messengerapp.util.ResultState
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

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

    override fun getCurrentUser(phoneNumber: String): Flow<ResultState<UserEntity>> = callbackFlow {
        trySend(ResultState.Loading())
        Log.d("user_phoneNumber", phoneNumber)
        firestore.collection("users")
            .whereEqualTo("phoneNumber", phoneNumber)
            .get()
            .addOnSuccessListener {
                val user = it.documents.first().toObject(UserEntity::class.java)
                if(user!=null) {
                    trySend(ResultState.Success(user))
                }
            }
            .addOnFailureListener{
                    trySend(ResultState.Error(it))
            }
        awaitClose {
            close()
        }
    }

    override fun getCurrentDocument(uid: String): Flow<ResultState<String>> {
        return  callbackFlow {
            trySend(ResultState.Loading())
            Log.d("user_uid_call", uid)
            firestore.collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener {
                    val doc = it.data?.entries.toString()
                    trySend(ResultState.Success(doc))
                }
                .addOnFailureListener {
                    trySend(ResultState.Error(it))
                }
                awaitClose {
                    close()
                }
        }
    }


    fun checkUserExists(phoneNumber: String): Flow<Boolean> {
        return flow<Boolean> {
            val querySnapshot = firestore.collection("users")
                .whereEqualTo("phoneNumber", phoneNumber)
                .get()
                .await()
            emit(!querySnapshot.isEmpty)
        }.flowOn(Dispatchers.IO)

    }


}