package com.example.messengerapp.presentation.viewmodel

import android.app.Activity
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messengerapp.data.entity.AuthData
import com.example.messengerapp.data.entity.UserEntity
import com.example.messengerapp.domain.AuthRepository
import com.example.messengerapp.domain.RegistrationRepository
import com.example.messengerapp.util.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class AuthViewModel  @Inject constructor(
    private val authRepository: AuthRepository,
    private val registrationRepository: RegistrationRepository,
): ViewModel()  {

    val authData: MutableState<AuthData?> = mutableStateOf(null)


    private val _userExists = MutableStateFlow<Boolean?>(null)
    val userExists = _userExists.asStateFlow()

    private val _currentUser = MutableStateFlow<UserEntity?>(null)
    val currentUser = _currentUser.asStateFlow()


    val userNumber : MutableState<String?> = mutableStateOf(null)


    fun signInWithCredential(
        otp: String
    ): Flow<ResultState<String>> {
        return authRepository.signWithCredential(otp = otp)
    }

    fun signUpUserWithPhoneNumber(
        phoneNumber: String,
        activity: Activity
    ): Flow<ResultState<String>> {
       return authRepository.verifyPhoneNumberWithOtp(phoneNumber, activity)
    }

//    suspend fun getCurrentUid() {
//        viewModelScope.launch(Dispatchers.IO) {
//            authRepository.getCurrentUserId().collect{ uid ->
//                Log.d("user_uid", uid)
//                _uid.value = uid
//                id.value = uid
//            }
//        }
//    }


    fun uploadImage(imageUri: Uri, userId: String): Flow<ResultState<String>> {
        return registrationRepository.uploadImage(imageUri,userId)
    }


    fun insertUser(user: UserEntity): Flow<ResultState<String>> {
        return registrationRepository.insert(user)
    }


    suspend fun checkUserExists(phoneNumber: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.checkUserExists(phoneNumber).collect{ userExists ->
                Log.d("user_exists", "$userExists")
                _userExists.value = userExists
            }
        }
    }



    fun getCurrentUser(phoneNumber: String) {
        Log.d("user_phone (getCurrentUser)", phoneNumber)
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.getCurrentUser(phoneNumber = phoneNumber).collect { currentUser ->
                when (currentUser) {
                    is ResultState.Loading -> {
                        Log.d("user_info", "Loading...")
                    }

                    is ResultState.Success -> {
                        Log.d("user_info", "${currentUser.data}")
                        _currentUser.value =  currentUser.data
//                        if(currentUser.data != null) {
//                           _currentUser.value =  currentUser.data
//                        }
                        Log.d("user_info", "${_currentUser.value}")
                    }

                    is ResultState.Error -> {}
                }
            }
        }
    }


    suspend fun getAuthData(){
        viewModelScope.launch(Dispatchers.IO) {
            authData.value = authRepository.getAuthData()
        }
    }

    suspend fun logOut(): Flow<ResultState<String>> {
        val logOutResult = viewModelScope.async(Dispatchers.IO) {
            authRepository.logOut()
        }.await()

        return logOutResult
    }


}