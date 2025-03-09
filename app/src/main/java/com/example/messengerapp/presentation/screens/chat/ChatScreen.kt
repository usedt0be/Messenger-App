package com.example.messengerapp.presentation.screens.chat

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.messengerapp.core.theme.AppTheme
import com.example.messengerapp.domain.models.Contact
import com.example.messengerapp.presentation.component.chat.ChatTextField
import com.example.messengerapp.presentation.component.chat.ChatTitle
import com.example.messengerapp.presentation.viewmodel.DialogChatViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun ChatScreen(
    contactId: String,
    chatViewModel: DialogChatViewModel,
    onTopBarClick: (Contact.Id) -> Unit,
    onClickBackToChats:() -> Unit,
    onSendMessageClick:() -> Unit
) {
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        Log.d("chat_dialog_effect", "Invoked")
        scope.launch(Dispatchers.IO) {
            chatViewModel.getContact(contactId)
        }
    }
    val scrollState = rememberLazyListState()

    var messageText by remember { mutableStateOf("") }

    val contact = chatViewModel.contact.collectAsState().value
    Log.d("chat_dialog_contact", "$contact")
    Scaffold(
        topBar = {
            contact?.let {
                ChatTitle(
                    contact = contact,
                    onClickBackToChats = {
                        onClickBackToChats()
                    },
                    modifier = Modifier.windowInsetsPadding(insets = WindowInsets.statusBars),
                    onChatTitleClick = {
                        onTopBarClick(
                            Contact.Id(value = contactId)
                        )
                    }
                )
            }
        },
        bottomBar = {
            ChatTextField(
                messageText = messageText,
                onMessageTextChanged = { message ->
                    messageText = message
                },
                onSendMessageClick = {
                    chatViewModel.sendMessage(text = messageText)
                },
                modifier = Modifier.fillMaxWidth()
                    .background(color = AppTheme.colors.backgroundSecondary)
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .windowInsetsPadding(WindowInsets.ime)
            )
        },
        contentWindowInsets = WindowInsets.ime
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues)

        ) {
            LazyColumn(
                modifier = Modifier.fillMaxHeight()
                    .weight(1f)
                    .background(color = AppTheme.colors.secondary)
            ) {

            }
        }
    }
}



