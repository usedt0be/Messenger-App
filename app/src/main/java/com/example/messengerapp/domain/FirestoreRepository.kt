package com.example.messengerapp.domain

import com.example.messengerapp.data.entity.UserEntity
import com.example.messengerapp.util.ResultState
import kotlinx.coroutines.flow.Flow

interface FirestoreRepository {
    fun insert(user: UserEntity): Flow<ResultState<String>>

    fun getCurrentUser(phoneNumber: String): Flow<ResultState<UserEntity>>

    fun checkUserExists(phoneNumber: String): Flow<Boolean>

//    fun getUsers(): Flow<ResultState<User>>


//    fun update(user: User): Flow<ResultState<User>>
}