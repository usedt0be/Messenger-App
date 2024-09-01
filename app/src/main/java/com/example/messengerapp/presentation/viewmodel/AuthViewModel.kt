package com.example.messengerapp.presentation.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.example.messengerapp.data.AuthRepositoryImpl
import com.example.messengerapp.util.ResultState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class AuthViewModel  @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl
): ViewModel()  {


    fun signInWithCredential(
        otp: String
    ): Flow<ResultState<String>> {
        return authRepositoryImpl.signWithCredential(otp = otp)
    }

    fun signUpUserWithPhoneNumber(
        phoneNumber: String,
        activity: Activity
    ): Flow<ResultState<String>> {
       return authRepositoryImpl.registerUserWithPhoneNumber(phoneNumber, activity)
    }
}