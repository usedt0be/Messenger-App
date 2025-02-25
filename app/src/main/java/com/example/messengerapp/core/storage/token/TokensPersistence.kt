package com.example.messengerapp.core.storage.token

import com.example.messengerapp.core.entity.AuthToken
import kotlinx.coroutines.flow.Flow

interface TokensPersistence {

//    val token : Flow<AuthToken?>

    fun saveToken(token: AuthToken): Flow<Unit>

    fun deleteToken(token: AuthToken): Flow<Unit>

    fun getToken(): Flow<AuthToken>
}