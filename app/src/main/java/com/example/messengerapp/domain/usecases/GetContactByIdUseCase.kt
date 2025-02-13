package com.example.messengerapp.domain.usecases

import com.example.messengerapp.domain.repository.UserStorageRepository
import javax.inject.Inject

class GetContactByIdUseCase @Inject constructor(
    private val userStorageRepository: UserStorageRepository
) {
    operator fun invoke(contactId: String) {}
}