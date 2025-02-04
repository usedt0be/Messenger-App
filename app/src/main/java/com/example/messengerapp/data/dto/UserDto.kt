package com.example.messengerapp.data.dto

import androidx.compose.runtime.Immutable


@Immutable
data class UserDto(
    val userId: String,
    val phoneNumber: String,
    val firstName: String,
    val secondName: String? = null,
    val imageUrl: String? = null,
    val contacts: List<ContactDto?> = emptyList()
)