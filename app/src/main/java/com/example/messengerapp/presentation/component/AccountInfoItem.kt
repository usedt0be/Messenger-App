package com.example.messengerapp.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.messengerapp.core.theme.AppTheme


@Composable
fun AccountInfoItem(
    onClick:() -> Unit,
    info: String,
    description: String,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .background(
                color = AppTheme.colors.backgroundSecondary
            )
            .padding(start = 8.dp, end = 8.dp)
    ) {
        Text(
            text = info,
            style = AppTheme.typography.body2,
            color = AppTheme.colors.textPrimary
        )

        Text(
            text = description,
            style = AppTheme.typography.caption2,
            color = AppTheme.colors.textTertiary,
            modifier = Modifier.padding(top = 6.dp)
        )
    }

}


@Preview
@Composable
fun ProfileItemPreview(){
    AccountInfoItem(
        onClick = {},
        info = "First field",
        description = "Description",
        modifier = Modifier
    )
}