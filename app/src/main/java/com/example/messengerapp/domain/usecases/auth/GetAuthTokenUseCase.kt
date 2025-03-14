package com.example.messengerapp.domain.usecases.auth

import com.example.messengerapp.domain.repository.AuthRepository
import javax.inject.Inject

class GetAuthTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(){
        val token = authRepository.getAuthToken()
    }
}