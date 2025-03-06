package com.example.messengerapp.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class MessageDto(
    val messageId: String?,
    val senderId: String,
    val text: String,
    val time: Long
)
