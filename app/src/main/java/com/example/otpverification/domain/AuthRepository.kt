package com.example.otpverification.domain

import android.app.Activity
import com.example.otpverification.util.ResultState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {


    fun registerUserWithPhoneNumber(
        phoneNumber:String,
        activity: Activity
    ): Flow<ResultState<String>>


    fun signWithCredential(
        otp:String
    ): Flow<ResultState<String>>

}