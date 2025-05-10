package com.example.messengerapp.presentation.screens.chats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.messengerapp.core.theme.AppTheme
import com.example.messengerapp.presentation.component.chat.ChatItem
import com.example.messengerapp.presentation.navigation.NavBottomBar


@Composable
fun ChatsListScreen(
    chatsViewModel: ChatsViewModel,
    navController: NavController = rememberNavController(),
    onClickChat: (String) -> Unit
) {
    val state = chatsViewModel.state

    Scaffold(bottomBar = {
        NavBottomBar(navController = navController)
    }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppTheme.colors.backgroundPrimary)
                .padding(paddingValues = it)
        ) {
            itemsIndexed(
                items = state.chats.sortedBy { it.first.messages?.lastOrNull()?.time },
                key = { _, chat -> chat.first.chatId.hashCode() }
            ) { index ,chat ->
                ChatItem(
                    chat = chat,
                    modifier = Modifier.padding(start = 4.dp),
                    onClickChat = { chatId ->
                        onClickChat(chatId)
                    }
                )
                if(index != state.chats.lastIndex) {
                    HorizontalDivider(
                        thickness = 1.dp,
                        modifier = Modifier.padding(start = 68.dp),
                        color = AppTheme.colors.textTertiary.copy(alpha = 0.8f)
                    )
                }
            }
        }
    }
}



