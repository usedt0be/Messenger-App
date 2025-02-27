package com.example.messengerapp.domain.usecases

import android.util.Log
import com.example.messengerapp.domain.models.Contact
import com.example.messengerapp.domain.repository.UserStorageRepository
import javax.inject.Inject

class GetContactByIdUseCase @Inject constructor(
    private val userStorageRepository: UserStorageRepository
) {
    suspend operator fun invoke(contactId: String): Contact? {
        Log.d("use_case_id", "$contactId")
       return userStorageRepository.getContactById(contactId)
    }
}