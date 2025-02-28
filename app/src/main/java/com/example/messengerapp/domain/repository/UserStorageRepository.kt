package com.example.messengerapp.domain.repository

import com.example.messengerapp.domain.models.Contact
import com.example.messengerapp.domain.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface UserStorageRepository {
//    val userFlow: Flow<User?>

    suspend fun saveUserToDataStore(user: User)

    suspend fun deleteUserFromDataStore()

    suspend fun getUserFromDataStore(): Flow<User?>

}