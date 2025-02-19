package com.example.messengerapp.data.dto

data class MessageDto(
    val authorId: String,
    val text: String,
    val authorName: String,
    val time: Long
)
