package com.example.messengerapp.data.entity

import androidx.compose.runtime.Immutable


@Immutable
data class ContactDto(
    val id: String,
    val firstName: String,
    val secondName: String?,
    val phoneNumber: String
)
