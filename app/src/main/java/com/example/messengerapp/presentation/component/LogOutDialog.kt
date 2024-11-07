package com.example.messengerapp.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.messengerapp.R
import com.example.messengerapp.core.theme.AppTheme


@Composable
fun LogOutDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {

    Dialog(
        onDismissRequest = {
            onDismiss()
        }
    ) {
        Column(
            modifier = modifier
                .background(
                    color = AppTheme.colors.backgroundSecondary,
                    shape = RoundedCornerShape(size = 16.dp)
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Log out",
                style = AppTheme.typography.body1,
                color = AppTheme.colors.textPrimary,
                modifier = Modifier.padding(12.dp)
            )

            Text(
                text = "Are you sure you want to log out ?",
                style = AppTheme.typography.caption1,
                color = AppTheme.colors.textPrimary,
                modifier = Modifier.padding(16.dp)

            )


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 18.dp, end = 18.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                TextButton(
                    onClick = { onConfirm() },
                ) {
                    Text(
                        text = "Log out",
                        style = AppTheme.typography.caption1,
                        color = AppTheme.colors.accentPrimary,
                        modifier = Modifier
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                TextButton(
                    onClick = { onDismiss() },
                    modifier = Modifier
                ) {
                    Text(
                        text = stringResource(R.string.cancel_log_out),
                        style = AppTheme.typography.caption1,
                        color = AppTheme.colors.accentPrimary
                    )
                }
            }
        }
    }
}



@Preview
@Composable
fun LogOutDialogPreview() {
    LogOutDialog(onDismiss = {}, onConfirm = {})
}