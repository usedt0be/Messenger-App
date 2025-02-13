package com.example.messengerapp.presentation.screens.chat

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun ChatScreen(

) {
    val scrollState = rememberLazyListState()

    Scaffold(
        topBar = {

        }
    ) { paddingValues ->
        LazyColumn(
            state = scrollState,
            reverseLayout = true,
            modifier = Modifier.padding(paddingValues),
        ) {

        }

    }
}