package com.example.messengerapp.domain

import com.example.messengerapp.data.dto.UserDto
import com.example.messengerapp.data.mappers.toContact
import com.example.messengerapp.data.mappers.toUserProto
import com.example.messengerapp.domain.models.User
import com.example.messengerapp.domain.repository.AuthRepository
import com.example.messengerapp.domain.repository.UserStorageRepository
import com.example.messengerapp.util.ResultState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject


class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userStorageRepository: UserStorageRepository
) {

    suspend operator fun invoke(phoneNumber: String) {
        val user = userStorageRepository.getUserFromDataStore().firstOrNull()

        if(user == null) {
            authRepository.getCurrentUser(phoneNumber).collect { resultState ->
                when(resultState) {
                    is ResultState.Success -> {
                        resultState.data?.let { userDto ->
                            val userProto = userDto.toUserProto()
                            userStorageRepository.saveUserToDataStore(userProto)
                            val contacts = userDto.contacts.map { it?.toContact() }
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