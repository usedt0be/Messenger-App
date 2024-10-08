package com.example.messengerapp.domain

import android.net.Uri
import com.example.messengerapp.util.ResultState
import kotlinx.coroutines.flow.Flow

interface StorageRepository {
    fun uploadImage(imageUri: Uri?, userId: String): Flow<ResultState<String>>
}