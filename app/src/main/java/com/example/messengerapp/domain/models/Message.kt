package com.example.messengerapp.domain.models

data class Message(
    val senderId: String,
    val text: String,
    val senderName: String,
    val time: Long
)
