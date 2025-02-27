package com.example.messengerapp.domain.usecases

import android.util.Log
import com.example.messengerapp.core.entity.AuthToken
import com.example.messengerapp.core.storage.token.TokensPersistence
import com.example.messengerapp.domain.repository.AuthRepository
import com.example.messengerapp.domain.repository.UserStorageRepository
import com.example.messengerapp.util.ResultState
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject


class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userStorageRepository: UserStorageRepository,
    private val tokensPersistence: TokensPersistence
) {

    val userFlow = userStorageRepository.userFlow

    suspend operator fun invoke(phoneNumber: String) {
        val userData = userFlow.firstOrNull()
        Log.d("user_LoginUseCase", "$userData")
        if(userData == null) {
            authRepository.getCurrentUser(phoneNumber).collect { resultState ->
                when(resultState) {
                    is ResultState.Success -> {
                        val token = authRepository.getAuthToken()
                        Log.d("userUseCase_token", "$token")
                        token?.let {
                            tokensPersistence.saveToken(AuthToken(value = it))
                        }
                        resultState.data?.let { user ->
                            Log.d("userUseCaseInvoked", "$user")
                            userStorageRepository.saveUserToDataStore(user)
                            userStorageRepository.getUserFromDataStore()
                            val contacts = user.contacts.filterNotNull()
                            userStorageRepository.insertAllContactsToDb(contacts = contacts)
                        }
                    }
                    is ResultState.Error -> {}
                    is ResultState.Loading -> {}
                }
            }
        }
    }
}