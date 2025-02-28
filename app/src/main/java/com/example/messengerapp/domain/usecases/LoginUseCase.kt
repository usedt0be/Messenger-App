package com.example.messengerapp.domain.usecases

import android.util.Log
import com.example.messengerapp.core.entity.AuthToken
import com.example.messengerapp.core.storage.token.TokensPersistence
import com.example.messengerapp.domain.models.User
import com.example.messengerapp.domain.repository.AuthRepository
import com.example.messengerapp.domain.repository.ContactsRepository
import com.example.messengerapp.domain.repository.UserStorageRepository
import com.example.messengerapp.util.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject


class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userStorageRepository: UserStorageRepository,
    private val contactsRepository: ContactsRepository,
    private val tokensPersistence: TokensPersistence
) {

        suspend operator fun invoke(phoneNumber: String):Flow<User?> {
            return flow {
                authRepository.getCurrentUser(phoneNumber)
                    .collect { resultState ->
                        when (resultState) {
                            is ResultState.Success -> {
                                val token = authRepository.getAuthToken()
                                Log.d("userUseCase_token", "$token")
                                token?.let {
                                    tokensPersistence.saveToken(AuthToken(value = it))
                                }
                                resultState.data?.let { user ->
                                    Log.d("userUseCaseInvoked", "$user")
                                    userStorageRepository.saveUserToDataStore(user)
                                    val contacts = user.contacts.filterNotNull()
                                    contactsRepository.insertAllContactsToDb(contacts = contacts)
                                    Log.d("userUseCaseInvoked", "collectWorking")
                                    val userFromStore = userStorageRepository.getUserFromDataStore().firstOrNull()
                                    Log.d("userUseCaseResult", "$userFromStore")
                                    emit(userFromStore)
                                }
                            }
                            is ResultState.Error -> {
                                Timber.tag("userUseCase_token").d("Error")
                            }
                            is ResultState.Loading -> {
                                Timber.tag("userUseCase_token").d("Loading")}
                            }
                        Log.d("userUseCaseInvoked", "collectWorking2")
                    }

            }
        }
    }





//    suspend operator fun invoke(phoneNumber: String) {
//        val userData = userStorageRepository.userFlow.firstOrNull()
//
//        Log.d("user_LoginUseCase", "$userData")
//        Log.d("user_LoginUseCase2", " ${userData?.id?.isEmpty()== true}, ${userData?.id?.isBlank()}")
//
//        if(userData == null || userData?.id?.isBlank() == null) {
//            authRepository.getCurrentUser(phoneNumber).collect { resultState ->
//                when(resultState) {
//                    is ResultState.Success -> {
//                        resultState.data?.let { user ->
//                            Log.d("userUseCaseInvoked", "$user")
//                            userStorageRepository.saveUserToDataStore(user)
//                            userStorageRepository.getUserFromDataStore()
//                            val contacts = user.contacts.filterNotNull()
//                            contactsRepository.insertAllContactsToDb(contacts = contacts)
//                        }
//                        val token = authRepository.getAuthToken()
//                        Log.d("userUseCase_token", "$token")
//                        token?.let {
//                            tokensPersistence.saveToken(AuthToken(value = it))
//                        }
//                    }
//                    is ResultState.Error -> {}
//                    is ResultState.Loading -> {}
//                }
//            }
//        }
//    }
//    val userFlow = userStorageRepository.userFlow
