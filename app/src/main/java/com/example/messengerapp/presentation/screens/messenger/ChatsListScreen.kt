package com.example.messengerapp.presentation.screens.messenger

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.messengerapp.presentation.navigation.NavBottomBar


@Composable
fun ChatsListScreen(navController: NavController) {
    Scaffold(
        bottomBar = {
            NavBottomBar(navController = navController)
        }
    ) { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
        ) {
            Text(text = "Contacts Screen")
        }

    }

}
