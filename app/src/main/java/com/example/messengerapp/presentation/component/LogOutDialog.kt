package com.example.messengerapp.presentation.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun LogOutDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    ) {

    AlertDialog(
        title = {
            Text(text = "Log out?")
        },
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = {onConfirm()}) {
                Text(
                    text = "Yes",
                    modifier = Modifier
                )
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss()}) {
                Text(
                    text = "No",
                    modifier = Modifier
                )

            }
        }
    )

}



@Preview
@Composable
fun LogOutDialogPreview() {
    LogOutDialog(onDismiss = {}, onConfirm = {})
}