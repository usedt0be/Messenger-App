package com.example.messengerapp.domain

import android.net.Uri
import com.example.messengerapp.data.entity.UserDto
import com.example.messengerapp.util.ResultState
import kotlinx.coroutines.flow.Flow

interface RegistrationRepository {
    fun insert(user: UserDto): Flow<ResultState<String>>

    fun uploadImage(imageUri: Uri?, userId: String): Flow<ResultState<String>>

}