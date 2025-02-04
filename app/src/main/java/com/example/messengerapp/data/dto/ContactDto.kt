package com.example.messengerapp.data.dto

import androidx.compose.runtime.Immutable


@Immutable
data class ContactDto(
    val id: String,
    val firstName: String,
    val secondName: String? = null,
    val phoneNumber: String,
    val photoUrl: String? = null
)
