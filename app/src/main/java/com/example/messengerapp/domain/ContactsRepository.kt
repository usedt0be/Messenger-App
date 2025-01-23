package com.example.messengerapp.domain

import com.example.messengerapp.data.entity.UserDto
import com.example.messengerapp.util.ResultState
import kotlinx.coroutines.flow.Flow

interface ContactsRepository {
    fun addContact(phoneNumber: String): Flow<ResultState<UserDto>>
}