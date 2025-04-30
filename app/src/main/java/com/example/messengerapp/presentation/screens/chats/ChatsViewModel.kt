package com.example.messengerapp.presentation.screens.chats

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messengerapp.domain.usecases.chat.GetChatsUseCase
import com.example.messengerapp.domain.usecases.chat.GetMessagesUseCase
import com.example.messengerapp.presentation.screens.chat_dialog.ChatDialogViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChatsViewModel @Inject constructor(
    private val getChatsUseCase: GetChatsUseCase,
    private val getMessagesUseCase: GetMessagesUseCase
): ViewModel() {

    var state by mutableStateOf(ChatsViewState())
        private set

    fun getChats(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getChatsUseCase.invoke(userId).forEach { chat ->
                val lastMessage = async {
                    getMessagesUseCase.invoke(chatId = chat.chatId, page = 1, limit = 1)
                }.await()
            }
        }
    }

}