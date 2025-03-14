package com.example.messengerapp.data.mappers

import com.example.messengerapp.data.dto.ContactDto
import com.example.messengerapp.data.entity.ContactEntity
import com.example.messengerapp.domain.models.Contact


fun ContactDto.toContactEntity() = ContactEntity(
    id = id,
    ownerId = ownerId,
    firstName = firstName ?: "",
    secondName = secondName,
    phoneNumber = phoneNumber ?: "",
    photo = photoUrl
)



fun ContactDto.toContact() = Contact(
    id = Contact.Id(value = id),
    ownerId = ownerId,
    firstName = firstName   ?:"",
    secondName = secondName ?: "",
    phoneNumber = phoneNumber ?: "",
    photo = photoUrl ?:"",
)



fun ContactEntity.toContact() = Contact(
    id = Contact.Id(value = id),
    ownerId = ownerId,
    firstName = firstName,
    secondName = secondName ?: "",
    phoneNumber = phoneNumber,
    photo = photo ?: ""
)


fun Contact.toContactEntity() = ContactEntity(
    id = id.value,
    ownerId = ownerId,
    firstName = firstName,
    secondName = secondName,
    phoneNumber = phoneNumber,
    photo = photo
)