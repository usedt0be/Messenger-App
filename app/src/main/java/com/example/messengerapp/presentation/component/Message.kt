package com.example.messengerapp.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.messengerapp.core.theme.AppTheme
import com.example.messengerapp.domain.models.Message
import com.example.messengerapp.util.AppUtils


@Composable
fun Message(
    message: Message,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart
) {
    val time = AppUtils.formatTimeStamp(message.time)
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment =  contentAlignment
    ) {
        Column(modifier = modifier) {
            Text(
                text = message.text,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp, end = 4.dp, ),
                style = AppTheme.typography.caption1,
                color = AppTheme.colors.textPrimary
            )

            Text(
                text = time,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp, end = 4.dp, bottom = 2.dp)
                    .align(Alignment.End),
                style = AppTheme.typography.caption2,
                color = AppTheme.colors.textPrimary
            )
        }
    }

}


@Preview
@Composable
fun MessagePreview() {
    Message(
        modifier = Modifier,
        message = Message(
            senderId = "",
            text = "Some text",
            time = 0L,
            messageId = "sfafsd"
        )
    )
}