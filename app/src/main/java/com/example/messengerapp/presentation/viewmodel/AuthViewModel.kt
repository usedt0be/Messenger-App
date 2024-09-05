package com.example.messengerapp.presentation.viewmodel

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messengerapp.data.AuthRepositoryImpl
import com.example.messengerapp.data.FirestoreRepositoryImpl
import com.example.messengerapp.data.firebase.UserEntity
import com.example.messengerapp.util.ResultState
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class AuthViewModel  @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl,
    private val firestoreRepositoryImpl: FirestoreRepositoryImpl
): ViewModel()  {

    private val _uid = MutableStateFlow<String>("")

    val uid : StateFlow<String> = _uid




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

    suspend fun getCurrentUid() {
        viewModelScope.launch {
            authRepositoryImpl.getCurrentUserId().collect{ uid ->
                Log.d("user_uid","$uid")
                _uid.value = uid
            }
        }
    }

    fun insertUser(user: UserEntity): Flow<ResultState<String>> {
        return firestoreRepositoryImpl.insert(user)
    }



}