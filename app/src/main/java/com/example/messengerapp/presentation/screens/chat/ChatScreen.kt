package com.example.messengerapp.presentation.screens.chat

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.messengerapp.core.theme.AppTheme
import com.example.messengerapp.core.viewmodel.daggerViewModel
import com.example.messengerapp.domain.models.Contact
import com.example.messengerapp.presentation.component.Message
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
    onClickBackToChats: () -> Unit,
) {
    val messages by chatViewModel.messages.collectAsState()
    Log.d("chat_messages_UI", "$messages")
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        Log.d("chat_dialog_effect", "Invoked")
        scope.launch(Dispatchers.IO) {
            chatViewModel.getContact(contactId)
        }
    }
    val scrollState = rememberLazyListState()

    var messageText by remember { mutableStateOf("") }
    Log.d("message_text", "$messageText")

    val contact = chatViewModel.contact.collectAsState().value

    Scaffold(topBar = {
        contact?.let {
            ChatTitle(contact = contact,
                onClickBackToChats = {
                    onClickBackToChats()
                    chatViewModel.disconnect()
                },
                modifier = Modifier.windowInsetsPadding(insets = WindowInsets.statusBars),
                onChatTitleClick = {
                    onTopBarClick(
                        Contact.Id(value = contactId)
                    )
                }
            )
        }
    }, bottomBar = {
        ChatTextField(messageText = messageText,
            onMessageTextChanged = { message ->
                messageText = message
            },
            onSendMessageClick = {
                chatViewModel.sendMessage(text = messageText)
                messageText = ""
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(color = AppTheme.colors.backgroundSecondary)
                .windowInsetsPadding(WindowInsets.navigationBars)
                .windowInsetsPadding(WindowInsets.ime)
        )
    }, contentWindowInsets = WindowInsets.ime
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .background(color = AppTheme.colors.backgroundPrimary)
                    .fillMaxSize()
                    .weight(1f),
                state = scrollState,
                reverseLayout = true
            ) {
                items(items = messages) { message ->
                    val isParticipantId = message.senderId == contactId
                    Log.d("chat_message_sender_contact_id", "sender ${message.senderId} , contact: $contactId")
                    Message(
                        message = message,
                        modifier = Modifier
                            .padding(start = 16.dp, top = 4.dp, bottom = 4.dp, end = 16.dp)
                            .heightIn(min = 40.dp)
                            .widthIn(min = 48.dp, max = 256.dp)
                            .background(
                                color = if (!isParticipantId) {
                                    AppTheme.colors.onSuccess
                                } else {
                                    AppTheme.colors.tertiary
                                }, shape = RoundedCornerShape(percent = 25)
                            ),
                        contentAlignment = if (isParticipantId) {
                                    Alignment.CenterStart
                                } else {
                                    Alignment.CenterEnd
                                }

                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ChatScreenPreview(){
    ChatScreen(
        onClickBackToChats = {},
        contactId = "dsad1",
        onTopBarClick = {},
        chatViewModel = daggerViewModel()
    )
}


