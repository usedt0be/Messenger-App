package com.example.messengerapp.domain.models

import androidx.compose.runtime.Immutable

@Immutable
data class User(
    val id: String,
    val firstName: String,
    val secondName: String?,
    val phoneNumber: String,
    val imageUrl: String,
    val contacts: List<Contact?> = emptyList()
)
