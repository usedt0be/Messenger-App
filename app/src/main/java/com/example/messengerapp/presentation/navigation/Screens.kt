package com.example.messengerapp.presentation.navigation

import com.example.messengerapp.R


sealed class Screens(
    val route: String,
) {

    data object SignUpScreen: Screens(route = "sign_up_screen")

    data object OtpScreen: Screens(route = "otp_screen")

    data object RegistrationScreen: Screens(route = "registration_screen")

    sealed class BottomScreens(route: String, val name: String, val icon: Int?):Screens(route) {
        data object ProfileScreen: BottomScreens(
            route = "profile_screen",
            name = "Settings",
            icon = R.drawable.settings_ic
        )

        data object ChatListScreen: BottomScreens(
            route = "chat_list",
            name = "Chats",
            icon = R.drawable.message_ic
        )

        data object ContactsScreen: BottomScreens(
            route = "contacts_screen",
            name = "Contacts",
            icon = R.drawable.contacts_ic
        )
    }

//    data object ProfileScreen: Screens(
//        route = "profile_screen",
//        name = "Settings",
//        icon = R.drawable.settings_ic
//    )
//
//    data object ChatListScreen: Screens(
//        route = "chat_list",
//        name = "Chats",
//        icon = R.drawable.message_ic
//        )
//
//    data object ContactsScreen: Screens(
//        route = "contacts_screen",
//        name = "Contacts",
//        icon = R.drawable.contacts_ic
//    )

}