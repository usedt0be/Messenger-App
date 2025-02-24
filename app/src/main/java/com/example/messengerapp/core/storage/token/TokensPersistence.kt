package com.example.messengerapp.core.storage.token

import kotlinx.coroutines.flow.Flow

interface TokensPersistence {

    val token: Flow<String?>

    fun saveToken(token: String): Flow<Unit>

    fun deleteToken(token: String): Flow<Unit>
}