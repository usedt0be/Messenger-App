package com.example.messengerapp.data.entity

import androidx.compose.runtime.Immutable


@Immutable
data class UserEntity(
    val userId: String? = "",
    val phoneNumber: String? = "",
    val firstName: String? = "",
    val secondName: String? = "",
    val imageUrl: String? = ""
)