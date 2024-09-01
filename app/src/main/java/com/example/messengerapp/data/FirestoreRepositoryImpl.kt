package com.example.messengerapp.data

import com.example.messengerapp.data.firebase.User
import com.example.messengerapp.domain.FirestoreRepository
import com.example.messengerapp.util.ResultState
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow

class FirestoreRepositoryImpl(
    private val firestore: FirebaseFirestore
): FirestoreRepository {
    override fun insert(user: User): Flow<ResultState<String>> {
        TODO("Not yet implemented")
    }

    override fun getUsers(): Flow<ResultState<User>> {
        TODO("Not yet implemented")
    }

    override fun update(user: User): Flow<ResultState<User>> {
        TODO("Not yet implemented")
    }
}