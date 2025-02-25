package com.example.messengerapp.domain.usecases

import com.example.messengerapp.core.entity.AuthToken
import com.example.messengerapp.core.storage.token.TokensPersistence
import com.example.messengerapp.core.storage.token.TokensPersistenceImpl
import com.example.messengerapp.domain.repository.UserStorageRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val tokensPersistence: TokensPersistence,
    private val userStorageRepository: UserStorageRepository

) {
    operator fun invoke(token: AuthToken) {
        tokensPersistence.deleteToken(token)
    }
}