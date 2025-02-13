package com.example.messengerapp.domain.usecases

import com.example.messengerapp.domain.models.Contact
import com.example.messengerapp.domain.repository.UserStorageRepository
import javax.inject.Inject

class DeleteContactUseCase @Inject constructor(
    private val userStorageRepository: UserStorageRepository
) {
    operator fun invoke(contact: Contact){
        userStorageRepository.deleteContact(contact)
    }
}