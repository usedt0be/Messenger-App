package com.example.messengerapp.data.dto

import androidx.compose.runtime.Immutable


@Immutable
data class ContactDto(
    val id: String? = null,
    val firstName: String? = null,
    val secondName: String? = null,
    val phoneNumber: String? = null,
    val photoUrl: String? = null
)

