package com.example.messengerapp.data.firebase

import androidx.compose.runtime.Immutable


@Immutable
data class User(
    val userId: String? = "",
    val firstName: String? = "",
    val secondName: String? = "",
    val imageUrl: String? = ""
) {
    fun toMap() {
        "user_id" to userId
        "first_name" to firstName
        "second_name" to secondName
        "image_url" to imageUrl
    }
}