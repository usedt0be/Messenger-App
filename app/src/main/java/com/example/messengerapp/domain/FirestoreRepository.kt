package com.example.messengerapp.domain

import com.example.messengerapp.data.firebase.User
import com.example.messengerapp.util.ResultState
import kotlinx.coroutines.flow.Flow

interface FirestoreRepository {

    fun insert(user: User): Flow<ResultState<String>>


    fun getUsers(): Flow<ResultState<User>>


    fun update(user: User): Flow<ResultState<User>>
}