package com.example.messengerapp.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.messengerapp.R
import com.example.messengerapp.core.theme.AppTheme


@Composable
fun ConfirmNumberDialog(
    onConfirm:() -> Unit,
    onDismiss: () -> Unit,
    number: String,
    modifier: Modifier = Modifier
) {

    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
            modifier = modifier
                .background(
                    color = AppTheme.colors.backgroundSecondary,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(start = 16.dp, top = 12.dp, end = 16.dp)
        ) {

            Text(
                text = stringResource(R.string.number_confirmation_promt),
                style = AppTheme.typography.caption1,
                color = AppTheme.colors.textSecondary,
                modifier = Modifier
            )

            Text(
                text = number,
                style = AppTheme.typography.body2,
                color = AppTheme.colors.textPrimary,
                modifier = Modifier.padding(top = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,

            ) {
                TextButton(
                    onClick = {
                        onDismiss()
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = AppTheme.colors.accentPrimary
                    ),
                ) {
                    Text(
                        text = stringResource(R.string.change_number),
                        style = AppTheme.typography.caption1,
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                TextButton(
                    onClick = {
                        onConfirm()
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = AppTheme.colors.accentPrimary
                    ),
                ) {
                    Text(
                        text = stringResource(R.string.number_is_correct),
                        style = AppTheme.typography.caption1,
                        modifier = Modifier,
                    )
                }
            }

        }
    }
}


@Preview
@Composable
fun ConfirmNumberDialogPreview() {
    ConfirmNumberDialog(
        onDismiss = {},
        onConfirm = {},
        number = "+7 954 321 7592 ",
        modifier = Modifier
    )
}