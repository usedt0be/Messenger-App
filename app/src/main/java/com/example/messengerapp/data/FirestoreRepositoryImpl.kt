package com.example.messengerapp.data

import com.example.messengerapp.data.firebase.User
import com.example.messengerapp.domain.FirestoreRepository
import com.example.messengerapp.util.ResultState
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirestoreRepositoryImpl(
    private val firestore: FirebaseFirestore
): FirestoreRepository {
    override fun insert(user: User): Flow<ResultState<String>> = callbackFlow {
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

//    override fun getUsers(udi: String): Flow<ResultState<User>> = callbackFlow {
//        trySend(ResultState.Loading())
//        firestore.collection("users")
//            .document(udi)
//            .get()
//            .addOnSuccessListener { querySnapshot ->
//                val user = querySnapshot.toObject()
//
//            }
//            .addOnFailureListener {
//                trySend(ResultState.Error(it))
//            }
//    }

    override fun update(user: User): Flow<ResultState<User>> {
        TODO("Not yet implemented")
    }
}