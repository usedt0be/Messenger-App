package com.example.messengerapp.domain.models

import androidx.compose.runtime.Immutable


@Immutable
data class Message(
    val messageId: String,
    val senderId: String,
    val text: String,
    val time: Long
)
