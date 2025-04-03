package com.example.messengerapp.data.dto

import androidx.compose.runtime.Immutable


@Immutable
data class UserDto(
    val userId: String? = null,
    val phoneNumber: String? = null,
    val firstName: String? = null,
    val secondName: String? = null,
    val imageUrl: String? = null,
    val contacts: List<ContactDto?> = emptyList()
)