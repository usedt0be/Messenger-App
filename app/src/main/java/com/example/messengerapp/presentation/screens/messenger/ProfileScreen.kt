package com.example.messengerapp.presentation.screens.messenger

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.messengerapp.R
import com.example.messengerapp.presentation.viewmodel.AuthViewModel


@Composable
fun ProfileScreen(authViewModel: AuthViewModel) {

    val currentUser = authViewModel.currentUser.collectAsState().value

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Spacer(modifier = Modifier.height(20.dp))

        Text("$currentUser")

        Spacer(modifier = Modifier.height(20.dp))

        Text("${currentUser?.phoneNumber}")
    }

}