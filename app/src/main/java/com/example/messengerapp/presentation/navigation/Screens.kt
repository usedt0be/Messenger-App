package com.example.messengerapp.presentation.navigation

import com.example.messengerapp.R


sealed class Screens(
    val route: String,
) {

    data object SignUpScreen: Screens(route = "sign_up_screen")

    data object OtpScreen: Screens(route = "otp_screen")

    data object RegistrationScreen: Screens(route = "registration_screen")

    sealed class BottomBarScreens(route: String, val name: String, val icon: Int?):Screens(route) {
        data object ProfileScreen: BottomBarScreens(
            route = "profile_screen",
            name = "Settings",
            icon = R.drawable.settings_ic
        )

        data object ChatListScreen: BottomBarScreens(
            route = "chat_list",
            name = "Chats",
            icon = R.drawable.message_ic
        )

        data object ContactsScreen: BottomBarScreens(
            route = "contacts_screen",
            name = "Contacts",
            icon = R.drawable.contacts_ic
        )
    }



}