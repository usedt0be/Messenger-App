package com.example.messengerapp.presentation.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.messengerapp.presentation.screens.auth.OtpScreen
import com.example.messengerapp.presentation.screens.auth.AuthScreen
import com.example.messengerapp.presentation.screens.auth.VerifyScreen
import com.example.messengerapp.presentation.screens.registration.RegistrationScreen
import com.example.messengerapp.presentation.viewmodel.AuthViewModel


@Composable
fun NavigationGraph(
    activity: Activity,
    factory: ViewModelProvider.Factory,
    authViewModel: AuthViewModel = viewModel(factory = factory)
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.RegistrationScreen.route
    ) {

        composable(
            route = Screens.SignUpScreen.route
        ) {
            AuthScreen(
                activity = activity,
                navController = navController,
                authViewModel = authViewModel,
            )
        }

        composable(
            route = Screens.OtpScreen.route
        ) {
            OtpScreen(
                navController =navController,
                authViewModel = authViewModel
            )
        }

        composable(
            route = Screens.RegistrationScreen.route
        ) {
            RegistrationScreen()
        }

        composable(
            route = Screens.VerifyScreen.route
        ) {
            VerifyScreen()
        }

    }
}