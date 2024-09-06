package com.example.messengerapp.domain

import com.example.messengerapp.data.firebase.UserEntity
import com.example.messengerapp.util.ResultState
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.Flow

interface FirestoreRepository {
    fun insert(user: UserEntity): Flow<ResultState<String>>

    fun getCurrentUser(phoneNumber: String): Flow<ResultState<UserEntity>>

    fun getCurrentDocument(uid: String): Flow<ResultState<String>>




//    fun getUsers(): Flow<ResultState<User>>


//    fun update(user: User): Flow<ResultState<User>>
}