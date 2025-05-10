package com.example.messengerapp.presentation.component.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.messengerapp.core.theme.AppTheme
import com.example.messengerapp.domain.models.Chat
import com.example.messengerapp.domain.models.ChatParticipant
import com.example.messengerapp.domain.models.Message
import com.example.messengerapp.util.AppUtils


@Composable
fun ChatItem(
    chat: Pair<Chat, ChatParticipant?>,
    modifier: Modifier = Modifier,
    onClickChat: (String) -> Unit
) {
    val painter = rememberAsyncImagePainter(model = chat.second?.photoUrl ?: "")
    val chatParticipant = chat.second
    val chatInfo = chat.first
    val lastMessage = chatInfo.messages?.last()
    val time = lastMessage?.let {
        AppUtils.formatMessageTime(it.time)
    }

    Row(
        modifier = modifier.fillMaxWidth()
            .height(80.dp)
            .clickable {
                chatParticipant?.let {
                    onClickChat(it.id)
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .height(64.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = chatParticipant?.firstName ?: "",
                    modifier = Modifier,
                    style = AppTheme.typography.body1,
                    color = AppTheme.colors.textPrimary
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "$time",
                    modifier = Modifier.padding(
                        end = 4.dp
                    ),
                    style = AppTheme.typography.caption1,
                    color = AppTheme.colors.textTertiary,
                )
            }

            chatInfo.messages?.let {
                if (lastMessage?.senderId != chatParticipant?.id) {
                    Text(
                        text = "you:",
                        modifier = Modifier.padding(top = 4.dp),
                        style = AppTheme.typography.caption1,
                        color = AppTheme.colors.textTertiary
                    )
                }

                Text(
                    text = lastMessage?.text ?: "",
                    modifier = Modifier.padding(top = 4.dp),
                    overflow = TextOverflow.Ellipsis,
                    style = AppTheme.typography.body2,
                    color = AppTheme.colors.textSecondary
                )
            }
        }
    }
}


@Preview
@Composable
fun ChatItemPreview() {
    ChatItem(
        chat = Pair(
            first = Chat(
                chatId = "124",
                participantsIds = listOf("someId"),
                messages = listOf(
                    Message(
                        messageId = "dd3",
                        senderId = "someId",
                        text = "first message",
                        time = 5135L
                    )
                )
            ),
            second = ChatParticipant(
                id = "someId",
                firstName = "User",
                photoUrl = "",
                phoneNumber = "79883394"
            )
        ),
        onClickChat = {}
    )
}