package com.example.messengerapp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ResponseMeta<T>(
    val message: String,
    val data: T
)


//authenticate {
//
//    get<ChatRoutes.GetDialogChat> { route ->
//
//        val myUserId = call.userId()
//
//        val dialogUserId = route.userId
//
//
//        val chat = chatService.getDialogChatByUsers(myUserId, dialogUserId)
//
//
//        call.respond(
//
//            status = HttpStatusCode.OK,
//
//            SuccessfulResponse(
//
//                message = "Successful.",
//
//                data = chat
//
//            )
//
//        )
//
//    }
//
//}