package com.example.messengerapp.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ProfileItem(
    modifier: Modifier = Modifier,
    onClick:() -> Unit,
    text: String,
    description: String
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {
        Text(
            text = text,
        )

        Text(
            text = description,
            modifier = Modifier.padding(top = 6.dp)
        )
    }

}