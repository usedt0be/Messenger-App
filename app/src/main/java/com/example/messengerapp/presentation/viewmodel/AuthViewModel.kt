package com.example.messengerapp.presentation.viewmodel

import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messengerapp.data.AuthRepositoryImpl
import com.example.messengerapp.data.FirestoreRepositoryImpl
import com.example.messengerapp.data.firebase.UserEntity
import com.example.messengerapp.util.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class AuthViewModel  @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl,
    private val firestoreRepositoryImpl: FirestoreRepositoryImpl
): ViewModel()  {


    private val _uid = MutableStateFlow<String?>(null)

    val uid : StateFlow<String?> = _uid

    private val _userNumber = MutableStateFlow<String?>(null)

    val userNumber = _userNumber

    private val _userNotExists = MutableStateFlow<Boolean?>(null)

    val userNotExists = _userNotExists.asStateFlow()



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

    suspend fun checkUserDoesNotExists(phoneNumber: String) {
        viewModelScope.launch {
            firestoreRepositoryImpl.checkUserDoesNotExists(phoneNumber).collect{ userExists ->
                Log.d("user_not_exists", "$userExists")
                _userNotExists.value = userExists
            }
        }
    }

    fun getUserInfo(uid: String) {
        viewModelScope.launch {
            firestoreRepositoryImpl.getCurrentUserDetails(uid).collect { resultState ->
                when (resultState) {
                    is ResultState.Loading -> {
                        Log.d("user_info", "Loading...")
                    }

                    is ResultState.Success -> {
                        val user = resultState.data
                        Log.d("user_info", "User info: ${user}")
                    }

                    is ResultState.Error -> {
                        Log.e("user_info", "pizdec...")
                    }

                }
            }
        }
    }


}