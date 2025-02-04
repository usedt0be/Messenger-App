package com.example.messengerapp.domain.repository

import android.app.Activity
import com.example.messengerapp.data.AuthData
import com.example.messengerapp.data.dto.UserDto
import com.example.messengerapp.util.ResultState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun verifyPhoneNumberWithOtp(
        phoneNumber:String,
        activity: Activity
    ): Flow<ResultState<String>>

    fun signWithCredential(
        otp:String
    ): Flow<ResultState<String>>

    fun logOut():Flow<ResultState<String>>

    fun checkUserExists(phoneNumber: String): Flow<Boolean>

    fun getCurrentUser(phoneNumber: String): Flow<ResultState<UserDto>>
}