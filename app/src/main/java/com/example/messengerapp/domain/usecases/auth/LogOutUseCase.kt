package com.example.messengerapp.domain.usecases.auth

import com.example.messengerapp.core.storage.token.TokensPersistence
import com.example.messengerapp.domain.repository.AuthRepository
import com.example.messengerapp.domain.repository.UserStorageRepository
import com.example.messengerapp.util.ResultState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokensPersistence: TokensPersistence,
    private val userStorageRepository: UserStorageRepository
) {

    suspend operator fun invoke(): Flow<ResultState<String>> {
        tokensPersistence.deleteToken()
        userStorageRepository.deleteUserFromDataStore()
        return authRepository.logOut()
    }
}