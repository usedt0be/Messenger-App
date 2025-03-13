package com.example.messengerapp.domain.models

data class Message(
    val messageId: String,
    val senderId: String,
    val text: String,
    val time: Long
)
