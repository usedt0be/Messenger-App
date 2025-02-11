package com.example.messengerapp.domain.usecases

import com.example.messengerapp.domain.models.Contact
import com.example.messengerapp.domain.repository.UserStorageRepository
import javax.inject.Inject

class GetContactsUseCase @Inject constructor(
    private val userStorageRepository: UserStorageRepository
) {
    operator fun invoke() : List<Contact> = userStorageRepository.getContactsFromDb()
}