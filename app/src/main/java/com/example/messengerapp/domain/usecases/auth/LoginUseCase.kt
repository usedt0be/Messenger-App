package com.example.messengerapp.domain.usecases.auth

import android.util.Log
import com.example.messengerapp.core.entity.AuthToken
import com.example.messengerapp.core.storage.token.TokensPersistence
import com.example.messengerapp.domain.models.User
import com.example.messengerapp.domain.repository.AuthRepository
import com.example.messengerapp.domain.repository.ContactsRepository
import com.example.messengerapp.domain.repository.UserStorageRepository
import com.example.messengerapp.util.ResultState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
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

    operator fun invoke(phoneNumber: String): Flow<User?> {
        return flow {
            authRepository.getCurrentUser(phoneNumber)
                .collect { resultState ->
                    when (resultState) {
                        is ResultState.Success -> {
                            resultState.data?.let { user ->
                                Log.d("userUseCaseInvoked", "$user")
                                userStorageRepository.saveUserToDataStore(user)
                                val contacts = user.contacts.filterNotNull()
                                contactsRepository.insertAllContactsToDb(contacts = contacts)
                                Log.d("userUseCaseInvoked", "collectWorking")
                                val userFromStore =
                                    userStorageRepository.getUserFromDataStore().firstOrNull()
                                Log.d("userUseCaseResult", "$userFromStore")
                                emit(userFromStore)
                                delay(200)
                                val token = authRepository.getAuthToken()
                                Log.d("userUseCase_token", "$token")
                                token?.let {
                                    tokensPersistence.saveToken(AuthToken(value = it)).first()
                                }
                            }
                        }

                        is ResultState.Error -> {
                            Timber.tag("userUseCase_token").d("Error")
                        }

                        is ResultState.Loading -> {
                            Timber.tag("userUseCase_token").d("Loading")
                        }
                    }
                }
        }
    }
}

