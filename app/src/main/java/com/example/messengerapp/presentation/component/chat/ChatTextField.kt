package com.example.messengerapp.presentation.component.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.messengerapp.R
import com.example.messengerapp.core.theme.AppTheme


@Composable
fun ChatTextField(
    messageText: String,
    onMessageTextChanged:(String) -> Unit,
    onSendMessageClick:(String) -> Unit,
    modifier: Modifier = Modifier
){

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,


    ) {
        OutlinedTextField(
            value = messageText,
            onValueChange = { messageText ->
                onMessageTextChanged(messageText)
            },
            textStyle = AppTheme.typography.body2,
            placeholder = {
                Text(
                    text = "Write your message",
                    style = AppTheme.typography.body2,
                    color = AppTheme.colors.textTertiary
                )
            },
            shape = RoundedCornerShape(percent = 50),
            modifier = Modifier.fillMaxWidth(0.82f)
                .heightIn(min = 48.dp, max = 124.dp)
                .padding(start = 16.dp, bottom = 8.dp),

            colors = TextFieldDefaults.colors(
                focusedTextColor = AppTheme.colors.textPrimary,
                focusedIndicatorColor = Color.Transparent,
                focusedContainerColor = AppTheme.colors.backgroundPrimary,
                cursorColor = AppTheme.colors.onSuccess,
                unfocusedTextColor = AppTheme.colors.textTertiary,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = AppTheme.colors.backgroundPrimary,
            )
        )

        IconButton(
            onClick = {
                onSendMessageClick(messageText)
            },
            modifier = Modifier
                .align(Alignment.Bottom)
                .padding(bottom = 8.dp, end = 16.dp)
                .background(
                    shape = CircleShape,
                    color = AppTheme.colors.textButton
                )
                .size(48.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.send_message_ic),
                tint = AppTheme.colors.onSuccess,
                contentDescription = null,
            )

        }
    }

}




@Composable
@Preview
fun ChatTextFieldPreview() {
    ChatTextField(
        messageText = "Some message",
        onMessageTextChanged = {},
        onSendMessageClick = {}
    )
}