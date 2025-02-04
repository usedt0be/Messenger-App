package com.example.messengerapp.data.mappers

import com.example.messengerapp.UserProto
import com.example.messengerapp.data.dto.UserDto
import com.example.messengerapp.domain.models.User


fun UserProto?.toUser() = if(this == null) null else {
    User(
        id = userId,
        firstName = firstName,
        secondName = secondName,
        phoneNumber = phoneNumber,
        imageUrl = imageUrl,
    )
}


fun UserDto.toUserProto() = UserProto.newBuilder()
    .setUserId(userId)
    .setPhoneNumber(phoneNumber)
    .setFirstName(firstName)
    .setSecondName(secondName)
    .setImageUrl(imageUrl)
    .build()



