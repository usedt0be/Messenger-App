package com.example.messengerapp.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun SnackBar(
    message: String
) {


    Snackbar(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 40.dp)
            .height(64.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        containerColor = MaterialTheme.colorScheme.error


    ) {
        Text(
            text = message,
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                .height(42.dp),
        )

    }

}



@Preview
@Composable
fun UpdateErrorMessagePreview() {
    SnackBar(message = "Please enter a valid number")
}