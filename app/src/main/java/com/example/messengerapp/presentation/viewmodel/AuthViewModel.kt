package com.example.messengerapp.presentation.viewmodel

import android.app.Activity
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messengerapp.data.entity.UserEntity
import com.example.messengerapp.domain.AuthRepository
import com.example.messengerapp.domain.FirestoreRepository
import com.example.messengerapp.domain.StorageRepository
import com.example.messengerapp.util.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class AuthViewModel  @Inject constructor(
    private val authRepository: AuthRepository,
    private val firestoreRepository: FirestoreRepository,
    private val storageRepository: StorageRepository,
): ViewModel()  {

    private val _uid = MutableStateFlow<String?>(null)
    val uid : StateFlow<String?> = _uid.asStateFlow()

    private val _userNumber = MutableStateFlow<String?>(null)
    val userNumber = _userNumber

    private val _userExists = MutableStateFlow<Boolean?>(null)
    val userExists = _userExists.asStateFlow()

    private val _currentUser = MutableStateFlow<UserEntity?>(null)
    val currentUser = _currentUser.asStateFlow()

    fun signInWithCredential(
        otp: String
    ): Flow<ResultState<String>> {
        return authRepository.signWithCredential(otp = otp)
    }

    fun signUpUserWithPhoneNumber(
        phoneNumber: String,
        activity: Activity
    ): Flow<ResultState<String>> {
       return authRepository.registerUserWithPhoneNumber(phoneNumber, activity)
    }

    suspend fun getCurrentUid() {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.getCurrentUserId().collect{ uid ->
                Log.d("user_uid","$uid")
                _uid.value = uid
            }
        }
    }

    fun uploadImage(imageUri: Uri, userId: String): Flow<ResultState<String>> {
        return storageRepository.uploadImage(imageUri,userId)
    }


    fun insertUser(user: UserEntity): Flow<ResultState<String>> {
        return firestoreRepository.insert(user)
    }


    suspend fun checkUserExists(phoneNumber: String) {
        viewModelScope.launch(Dispatchers.IO) {
            firestoreRepository.checkUserExists(phoneNumber).collect{ userExists ->
                Log.d("user_exists", "$userExists")
                _userExists.value = userExists
            }
        }
    }



    fun getCurrentUser(phoneNumber: String) {
        viewModelScope.launch(Dispatchers.IO) {
            firestoreRepository.getCurrentUser(phoneNumber = phoneNumber).collect { currentUser ->
                when (currentUser) {
                    is ResultState.Loading -> {
                        Log.d("user_info", "Loading...")
                    }

                    is ResultState.Success -> {
                        Log.d("user_info", "${currentUser.data}")
                        if(currentUser.data != null) {
                           _currentUser.value =  currentUser.data
                        }
                        Log.d("user_info", "${_currentUser.value}")
                    }

                    is ResultState.Error -> {}
                }
            }
        }
    }

}