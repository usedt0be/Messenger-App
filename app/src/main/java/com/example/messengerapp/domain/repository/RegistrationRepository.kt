package com.example.messengerapp.domain.repository

import android.net.Uri
import com.example.messengerapp.data.AuthData
import com.example.messengerapp.data.dto.UserDto
import com.example.messengerapp.util.ResultState
import kotlinx.coroutines.flow.Flow

interface RegistrationRepository {
    fun insert(user: UserDto): Flow<ResultState<String>>

    fun uploadImage(imageUri: Uri?, userId: String): Flow<ResultState<String>>

    suspend fun getRegistrationAuthData(): AuthData
}