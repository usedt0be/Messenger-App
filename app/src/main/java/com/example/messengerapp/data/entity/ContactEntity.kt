package com.example.messengerapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey
    val id: String,
    val ownerId: String,
    val firstName: String,
    val secondName: String?,
    val phoneNumber: String,
    val photo: String? = null
)
