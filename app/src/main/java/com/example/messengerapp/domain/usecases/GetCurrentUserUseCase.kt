package com.example.messengerapp.domain.usecases

import android.util.Log
import com.example.messengerapp.domain.models.User
import com.example.messengerapp.domain.repository.AuthRepository
import com.example.messengerapp.util.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
   private val authRepository: AuthRepository
) {
    operator fun invoke(phoneNumber: String): Flow<User?> {
        return flow { authRepository.getCurrentUser(phoneNumber)
            .collect { currentUser ->
                when (currentUser) {
                    is ResultState.Loading -> {}
                    is ResultState.Success -> {
                        emit(currentUser.data)
                        Log.d("user_info", "${currentUser.data}")
                    }

                    is ResultState.Error -> {}
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}