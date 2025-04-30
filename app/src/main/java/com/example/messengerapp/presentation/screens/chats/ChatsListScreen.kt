package com.example.messengerapp.presentation.screens.chats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.messengerapp.core.theme.AppTheme
import com.example.messengerapp.presentation.navigation.NavBottomBar


@Composable
fun ChatsListScreen(
    navController: NavController = rememberNavController()
) {
    Scaffold(bottomBar = {
        NavBottomBar(navController = navController)
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppTheme.colors.backgroundPrimary)
                .padding(paddingValues = it)
        ) {
            Text(text = "Chat List Screen")
        }
    }

}


@Preview
@Composable
fun ChatsListScreenPreview() {
    ChatsListScreen()
}
