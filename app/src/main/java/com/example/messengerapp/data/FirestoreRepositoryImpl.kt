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

    override fun getCurrentUserDetails(uid: String): Flow<ResultState<List<UserEntity>>> = callbackFlow {
        trySend(ResultState.Loading())
        Log.d("user_uid_call", uid)
        firestore.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener {document ->
               val user = document.data?.map {
                   UserEntity(
                       userId = document["userId"] as String?,
                       phoneNumber = document["phoneNumber"] as String?,
                       firstName = document["firstName"] as String?,
                       secondName = document["secondName"] as String?,
                       imageUrl = document["imageUrl"] as String?

                   )
               }

                trySend(ResultState.Success(user!!))
//                Log.d("user_", "${user?.values}")
            }
            .addOnFailureListener{
                    trySend(ResultState.Error(it))
            }
        awaitClose {
            close()
        }
    }

    override fun checkUserExists(uid: String): Flow<Boolean> {
        return  flow {
            val documentSnapshot =firestore.collection("users").document(uid).get()
            emit(documentSnapshot.result.exists())
        }.flowOn(Dispatchers.IO)
    }

    fun checkUserDoesNotExists(phoneNumber: String): Flow<Boolean> {
        return flow<Boolean> {
            val querySnapshot = firestore.collection("users")
                .whereEqualTo("phoneNumber", phoneNumber)
                .get()
                .await()
            emit(querySnapshot.isEmpty)
        }.flowOn(Dispatchers.IO)

    }

}