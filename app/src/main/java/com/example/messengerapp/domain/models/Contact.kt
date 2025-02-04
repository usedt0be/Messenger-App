package com.example.messengerapp.domain.models

data class Contact(
    val id: String,
    val firstName: String,
    val secondName: String? = null,
    val phoneNumber: String,
    val photo: String? = null
)