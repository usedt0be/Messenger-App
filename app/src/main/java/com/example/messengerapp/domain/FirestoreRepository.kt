package com.example.messengerapp.domain

import com.example.messengerapp.data.firebase.UserEntity
import com.example.messengerapp.util.ResultState
import kotlinx.coroutines.flow.Flow

interface FirestoreRepository {

    fun insert(user: UserEntity): Flow<ResultState<String>>



    fun getCurrentUserDetails(uid: String): Flow<ResultState<UserEntity>>



//
//    fun getUsers(): Flow<ResultState<User>>


//    fun update(user: User): Flow<ResultState<User>>
}