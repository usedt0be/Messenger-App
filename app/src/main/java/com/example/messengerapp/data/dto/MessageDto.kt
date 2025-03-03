package com.example.messengerapp.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class MessageDto(
    val senderId: String,
    val text: String,
    val senderName: String,
    val time: Long
)
