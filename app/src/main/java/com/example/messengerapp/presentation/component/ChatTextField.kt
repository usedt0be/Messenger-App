package com.example.messengerapp.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import kotlin.math.max
import kotlin.math.min


@Composable
fun ChatTextField(
    messageText: String,
    onMessageTextChanged:(String) -> Unit,
    onSendMessageClick:() -> Unit,
    modifier: Modifier = Modifier
){

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = AppTheme.colors.backgroundSecondary),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        OutlinedTextField(
            value = messageText,
            onValueChange = { messageText ->
                onMessageTextChanged(messageText)
            },
            placeholder = {
                Text(
                    text = "Write your message",
                    style = AppTheme.typography.caption1,
                    color = AppTheme.colors.textTertiary
                )
            },
            modifier = Modifier.fillMaxWidth(0.82f)
                .heightIn(min = 48.dp, max = 124.dp)
                .padding(start = 16.dp),

            colors = TextFieldDefaults.colors(
                focusedTextColor = AppTheme.colors.textPrimary,
                focusedIndicatorColor = Color.Transparent,
                focusedContainerColor = AppTheme.colors.backgroundSecondary,
                cursorColor = AppTheme.colors.onSuccess,
                unfocusedTextColor = AppTheme.colors.textPrimary,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = AppTheme.colors.backgroundSecondary,
            )
        )

        IconButton(
            onClick = {
                onSendMessageClick()
            },
            modifier = Modifier
                .align(Alignment.Bottom)
                .padding(bottom = 4.dp, end = 8.dp)
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