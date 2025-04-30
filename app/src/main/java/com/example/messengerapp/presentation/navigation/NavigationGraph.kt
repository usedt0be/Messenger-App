package com.example.messengerapp.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.messengerapp.core.viewmodel.daggerViewModel
import com.example.messengerapp.domain.models.Contact
import com.example.messengerapp.presentation.screens.auth.AuthScreen
import com.example.messengerapp.presentation.screens.auth.OtpScreen
import com.example.messengerapp.presentation.screens.chat_dialog.ChatScreen
import com.example.messengerapp.presentation.screens.chats.ChatsListScreen
import com.example.messengerapp.presentation.screens.contacts.ContactDetailsScreen
import com.example.messengerapp.presentation.screens.contacts.ContactsListScreen
import com.example.messengerapp.presentation.screens.profile.ProfileScreen
import com.example.messengerapp.presentation.screens.registration.RegistrationScreen
import com.example.messengerapp.presentation.viewmodel.AuthViewModel
import com.example.messengerapp.presentation.screens.chat_dialog.ChatDialogViewModel
import com.example.messengerapp.presentation.viewmodel.ContactDetailsViewModel
import com.example.messengerapp.presentation.viewmodel.ContactsViewModel
import com.google.firebase.auth.FirebaseAuth


@Composable
fun NavigationGraph(
    firebaseAuth: FirebaseAuth,
    factory: ViewModelProvider.Factory,
    authViewModel: AuthViewModel = viewModel(factory = factory),
) {
    val navController = rememberNavController()

    val currentUser = firebaseAuth.currentUser

    Log.d("AuthUser_StartApp", "${currentUser?.uid}")

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
                contactsViewModel = contactsViewModel,
                onClickContact = { contactId ->
                    navController.navigate(route = Screens.DialogChatScreen.route + "/${contactId.value}")
                },
            )
        }



        composable(
            route = Screens.DialogChatScreen.route + "/{chat_participant_id}",
            arguments = listOf(
                navArgument("chat_participant_id") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val chatParticipantId = backStackEntry.arguments?.getString("chat_participant_id") ?: "000"
            val chatViewModel: ChatDialogViewModel = daggerViewModel()
            Log.d("contactId_dialog", "$chatParticipantId")
            ChatScreen(
                contactId = chatParticipantId,
                chatDialogViewModel = chatViewModel,
                onClickBackToChats = {
                    navController.popBackStack()
                },
                onTopBarClick = { id ->
                    navController.navigate(
                        Screens.ContactDetailsScreen.route + "/${id.value}"
                    )
                }
            )
        }



        composable(
            route = Screens.ContactDetailsScreen.route + "/{id}"
        ) { backStackEntry ->
            val contactId = backStackEntry.arguments?.getString("id") ?: "000"
            val contactDetailsViewModel: ContactDetailsViewModel = viewModel(factory = factory)
            ContactDetailsScreen(
                contactId = Contact.Id(value = contactId),
                onClickBackward = {
                    navController.popBackStack()
                },
                contactDetailsViewModel = contactDetailsViewModel
            )
        }
    }
}