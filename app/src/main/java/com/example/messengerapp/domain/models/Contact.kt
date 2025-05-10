package com.example.messengerapp.domain.models

import androidx.compose.runtime.Immutable

@Immutable
data class Contact(
    val id: Id,
    val ownerId: String,
    val firstName: String,
    val secondName: String?,
    val phoneNumber: String,
    val photo: String
) {
    @JvmInline
    value class Id(val value: String)
}