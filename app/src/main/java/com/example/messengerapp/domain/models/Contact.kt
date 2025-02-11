package com.example.messengerapp.domain.models

data class Contact(
    val id: Id,
    val firstName: String,
    val secondName: String? = null,
    val phoneNumber: String,
    val photo: String? = null
) {
    @JvmInline
    value class Id(val value: String)
}