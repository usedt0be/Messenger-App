package com.example.messengerapp.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.messengerapp.core.theme.AppTheme
import com.example.messengerapp.domain.models.Message
import com.example.messengerapp.util.AppUtils


@Composable
fun Message(
    message: Message,
    modifier: Modifier = Modifier
){
    val time = AppUtils.formatTimeStamp(message.time)

    Column(
        modifier = Modifier
            .fillMaxWidth(0.4f),
    ) {
        Text(
            text = message.text,
            style = AppTheme.typography.caption1,
            color = AppTheme.colors.textPrimary
        )

        Text(
            text = time,
            modifier = Modifier.padding(top = 4.dp),
            style = AppTheme.typography.caption2,
            color = AppTheme.colors.tertiary,

        )
    }
}


@Preview
@Composable
fun MessagePreview(){
    Message(
        modifier = Modifier,
        message = Message(
            senderId = "",
            text = "Some text",
            time = 0L
        )
    )
}