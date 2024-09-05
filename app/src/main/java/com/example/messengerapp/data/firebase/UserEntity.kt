package com.example.messengerapp.data.firebase

import androidx.compose.runtime.Immutable


@Immutable
data class UserEntity(
    val userId: String? = "",
    val phoneNumber: String? = "",
    val firstName: String? = "",
    val secondName: String? = "",
    val imageUrl: String? = ""
) {
    fun toMap() {
        "userId" to userId
        "phoneNumber" to phoneNumber
        "firstName" to firstName
        "secondName" to secondName
        "imageUrl" to imageUrl
    }
}