package com.example.messengerapp.domain

import com.example.messengerapp.util.ResultState
import kotlinx.coroutines.flow.Flow

interface ContactsRepository {

    fun addContact(firstName:String, secondName: String?, phoneNumber: String): Flow<ResultState<String>>
}