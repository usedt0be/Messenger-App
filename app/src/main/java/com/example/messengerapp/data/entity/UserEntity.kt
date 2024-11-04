package com.example.messengerapp.data.entity

import androidx.compose.runtime.Immutable


@Immutable
data class UserEntity(
    val userId: String? = null,
    val phoneNumber: String? = null,
    val firstName: String? = null,
    val secondName: String? = null,
    val imageUrl: String? = null
)