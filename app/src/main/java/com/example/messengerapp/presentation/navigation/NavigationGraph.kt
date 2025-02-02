package com.example.messengerapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.messengerapp.presentation.screens.auth.AuthScreen
import com.example.messengerapp.presentation.screens.auth.OtpScreen
import com.example.messengerapp.presentation.screens.messenger.ChatsListScreen
import com.example.messengerapp.presentation.screens.messenger.ContactsListScreen
import com.example.messengerapp.presentation.screens.messenger.ProfileScreen
import com.example.messengerapp.presentation.screens.registration.RegistrationScreen
import com.example.messengerapp.presentation.viewmodel.AuthViewModel
import com.example.messengerapp.presentation.viewmodel.ContactsViewModel
import com.google.firebase.auth.FirebaseAuth


@Composable
fun NavigationGraph(
    firebaseAuth: FirebaseAuth,
    factory: ViewModelProvider.Factory,
    authViewModel: AuthViewModel = viewModel(factory = factory)
) {
    val navController = rememberNavController()

    val currentUser = firebaseAuth.currentUser

    NavHost(
        navController = navController,
        startDestination = if(currentUser != null) {
            authViewModel.getCurrentUser(currentUser.phoneNumber!!)
            Screens.BottomBarScreens.ProfileScreen.route
        } else {
            Screens.SignUpScreen.route
        }
    ) {
        composable(
            route = Screens.SignUpScreen.route
        ) {
            AuthScreen(
//                activity = activity,
                navController = navController,
                authViewModel = authViewModel,
            )
        }

        composable(
            route = Screens.OtpScreen.route
        ) {
            OtpScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(
            route = Screens.RegistrationScreen.route
        ) {
            RegistrationScreen(
                navController = navController,
                authViewModel = authViewModel,
            )
        }

        
        composable(
            route = Screens.BottomBarScreens.ProfileScreen.route
        ) {
            ProfileScreen(
                navController = navController,
                authViewModel = authViewModel,
            )
        }
        
        composable (route = Screens.BottomBarScreens.ChatListScreen.route ) {
            ChatsListScreen(navController = navController)
        }


        composable (route = Screens.BottomBarScreens.ContactsScreen.route ) {
            val contactsViewModel: ContactsViewModel = viewModel(factory = factory)
            ContactsListScreen(
                navController = navController,
                contactsViewModel = contactsViewModel
            )
        }
    }
}