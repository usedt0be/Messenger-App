package com.example.messengerapp.presentation.screens.contacts

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.messengerapp.R


@Composable
fun ContactDropDownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onClickDelete: () -> Unit,
) {

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest
    ) {
        DropdownMenuItem(
            text = {Text(text = "Delete")},
            leadingIcon = {
                Icon(
                    ImageVector.vectorResource(id = R.drawable.delete),
                    contentDescription = null,
                )
            },
            onClick = {
                onClickDelete()
            }
        )
    }
}