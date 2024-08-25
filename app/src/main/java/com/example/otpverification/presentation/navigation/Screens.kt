package com.example.otpverification.presentation.navigation

sealed class Screens(val route: String) {

    data object VerifyScreen: Screens(route = "verify_screen")

    data object SignUpScreen: Screens(route = "sign_up_screen")

    data object OtpScreen: Screens(route = "otp_screen")
}