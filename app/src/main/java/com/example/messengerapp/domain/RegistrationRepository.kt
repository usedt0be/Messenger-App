package com.example.messengerapp.domain

import android.net.Uri
import com.example.messengerapp.data.entity.UserEntity
import com.example.messengerapp.util.ResultState
import kotlinx.coroutines.flow.Flow

interface RegistrationRepository {
    fun insert(user: UserEntity): Flow<ResultState<String>>

    fun uploadImage(imageUri: Uri?, userId: String): Flow<ResultState<String>>

}