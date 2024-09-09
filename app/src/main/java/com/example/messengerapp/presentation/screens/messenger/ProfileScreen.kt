package com.example.messengerapp.presentation.screens.messenger

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.messengerapp.presentation.viewmodel.AuthViewModel


@Composable
fun ProfileScreen(authViewModel: AuthViewModel) {

    val currentUser = authViewModel.currentUser.collectAsState().value

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Spacer(modifier = Modifier.height(20.dp))

        val painter = rememberAsyncImagePainter(model = currentUser?.imageUrl)

        Image(painter = painter, contentDescription = "")

        Text("$currentUser")

        Spacer(modifier = Modifier.height(20.dp))

        Text("${currentUser?.phoneNumber}")
    }

}