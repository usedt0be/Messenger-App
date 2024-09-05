package com.example.messengerapp.presentation.screens.messenger

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.messengerapp.presentation.viewmodel.AuthViewModel


@Composable
fun ProfileScreen(authViewModel: AuthViewModel) {

    LaunchedEffect(key1 = Unit) {
        authViewModel.getCurrentUid()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Button(onClick = {
            authViewModel.getUserInfo(authViewModel.uid.value.toString())
        }) {
            Text(text = "Get info")
        }
    }

}