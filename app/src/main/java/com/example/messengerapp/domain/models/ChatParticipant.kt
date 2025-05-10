package com.example.messengerapp.domain.models

import androidx.compose.runtime.Immutable

@Immutable
data class ChatParticipant(
    val id: String,
    val firstName: String,
    val secondName: String? = "",
    val photoUrl: String,
    val phoneNumber: String
)