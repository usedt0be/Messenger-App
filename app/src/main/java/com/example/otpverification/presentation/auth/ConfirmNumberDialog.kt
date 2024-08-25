package com.example.otpverification.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


@Composable
fun ConfirmNumberDialog(
    onConfirm:() -> Unit,
    onDismiss: () -> Unit,
    number: String
) {

    Dialog(onDismissRequest = {onDismiss()}) {
        Column(modifier = Modifier
            .padding(start = 12.dp , end = 12.dp, top = 12.dp)
        ){
            Text(
                text = "Правильно ли указан номер?"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = number
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(
                    onClick = {
                        onDismiss()
                    }
                ) {
                    Text(text = "Изменить")
                }

                TextButton(
                    onClick = {
                        onConfirm()
                    }
                ) {
                    Text(text = "Да")
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
        number = "+7 954 321 7592 "
    )
}